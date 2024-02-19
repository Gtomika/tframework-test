/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.TFramework;
import org.tframework.core.elements.dependency.resolver.DependencyResolverAggregator;
import org.tframework.core.elements.dependency.resolver.DependencyResolversFactory;
import org.tframework.test.commons.utils.RootClassFinder;

/**
 * Given a valid and prepared {@link TestConfig}, this launcher starts the test TFramework application.
 */
@Slf4j
public class TestApplicationLauncher {

    private static final String UNEXPECTED_START_ERROR = "Application initialization was expected to fail, but it succeeded!";

    private final RootClassFinder rootClassFinder;

    public TestApplicationLauncher(RootClassFinder rootClassFinder) {
        this.rootClassFinder = rootClassFinder;
    }

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
        var launchResult = launchTestApplicationInternal(testConfig);
        checkUnexpectedApplicationState(testConfig.expectInitializationFailure(), launchResult);
        return launchResult;
    }

    private LaunchResult launchTestApplicationInternal(TestConfig testConfig) throws Exception {
        try {
            var application = TFramework.start(
                    testConfig.applicationName(),
                    rootClassFinder.findRootClass(testConfig),
                    testConfig.commandLineArguments().toArray(new String[0])
            );

            //these resolvers can be used to inject parameters into test methods
            var dependencyResolver = DependencyResolverAggregator.usingResolvers(List.of(
                    DependencyResolversFactory.createElementDependencyResolver(application.getElementsContainer()),
                    DependencyResolversFactory.createPropertyDependencyResolver(application.getPropertiesContainer())
            ));

            return new SuccessfulLaunchResult(application, dependencyResolver);
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

}
