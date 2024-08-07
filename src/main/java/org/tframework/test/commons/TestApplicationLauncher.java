/*
Copyright 2024 Tamas Gaspar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.tframework.test.commons;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.Application;
import org.tframework.core.TFramework;
import org.tframework.core.elements.context.ElementContext;
import org.tframework.core.elements.dependency.resolver.DependencyResolverAggregator;
import org.tframework.core.elements.dependency.resolver.DependencyResolversFactory;
import org.tframework.test.commons.utils.RootClassFinder;

/**
 * Given a valid and prepared {@link TestConfig}, this launcher starts the test TFramework application.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TestApplicationLauncher {

    static final String UNEXPECTED_START_ERROR = "Application initialization was expected to fail, but it succeeded!";

    private final RootClassFinder rootClassFinder;
    private final AppLauncher appLauncher;

    /**
     * Launches the test application. This will catch any exception during application start, and
     * check if a failed application start was to be expected ({@link TestConfig#expectInitializationFailure()}).
     * @param testConfig Prepared {@link TestConfig} to launch from.
     * @return {@link LaunchResult} with useful data for further processing this application.
     * @throws IllegalStateException If the application was expected to fail, but it successfully launched.
     * @throws Exception If the application was expected to succeed, but failed to launch, the initialization
     * exception is thrown.
     */
    public LaunchResult launchTestApplication(TestConfig testConfig) throws Exception {
        log.debug("Attempting to launch test application with the following config: {}", testConfig);
        var launchResult = launchTestApplicationInternal(testConfig);
        log.debug("The test application launch finished. Result: {}", launchResult);

        checkUnexpectedApplicationState(testConfig.expectInitializationFailure(), launchResult);
        return launchResult;
    }

    private LaunchResult launchTestApplicationInternal(TestConfig testConfig) {
        var rootClass = rootClassFinder.findRootClass(testConfig);
        try {
            log.info("""
                    \n-------------------------------------------------------------------
                    Launching application '{}' for tests.
                    -------------------------------------------------------------------
                    """, testConfig.applicationName());
            var application = appLauncher.startApp(testConfig, rootClass);
            log.info("""
                    \n-------------------------------------------------------------------
                    Launching of app '{}' finished. Retuning control to the test application launcher.
                    -------------------------------------------------------------------
                    """, testConfig.applicationName());

            //these resolvers can be used to inject parameters into test methods
            var dependencyResolver = DependencyResolverAggregator.usingResolvers(List.of(
                    DependencyResolversFactory.createElementDependencyResolver(application.getElementsContainer()),
                    DependencyResolversFactory.createPropertyDependencyResolver(application.getPropertiesContainer())
            ));

            var testClassElement = getTestApplicationElementContext(application, testConfig.testClass());
            return new SuccessfulLaunchResult(application, dependencyResolver, testClassElement);
        } catch (Exception e) {
            return new FailedLaunchResult(e);
        }
    }

    private void checkUnexpectedApplicationState(boolean expectFailure, LaunchResult launchResult) throws Exception {
        if(launchResult.successfulLaunch() && expectFailure) {
            throw new IllegalStateException(UNEXPECTED_START_ERROR);
        }
        if(!expectFailure && launchResult instanceof FailedLaunchResult failedLaunchResult) {
            //if the app expected to start, but did not, transiently rethrow the original exception
            throw failedLaunchResult.initializationException();
        }
    }

    public Optional<ElementContext> getTestApplicationElementContext(Application application, Class<?> testClass) {
        try {
            return Optional.of(application.getElementsContainer().getElementContext(testClass));
        } catch (Exception e) {
            log.warn("Cannot find test class' element context. This may be because elements are completely disabled");
            return Optional.empty();
        }
    }

    /**
     * This class exists so that launcher can be unit tested without having to start actual apps.
     */
    public static class AppLauncher {

        public Application startApp(TestConfig testConfig, Class<?> rootClass) {
            return TFramework.start(
                    testConfig.applicationName(),
                    rootClass,
                    testConfig.commandLineArguments().toArray(new String[0]),
                    testConfig.preConstructedElements()
            );
        }

    }

}
