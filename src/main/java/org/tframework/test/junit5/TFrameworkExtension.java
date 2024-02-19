/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import java.lang.reflect.Constructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstanceFactory;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;
import org.junit.jupiter.api.extension.TestInstantiationException;
import org.tframework.core.Application;
import org.tframework.core.TFramework;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.dependency.DependencyDefinition;
import org.tframework.core.elements.dependency.InjectAnnotationScanner;
import org.tframework.core.elements.dependency.graph.ElementDependencyGraph;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;
import org.tframework.test.commons.FailedLaunchResult;
import org.tframework.test.commons.LaunchResult;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.TestApplicationLauncher;
import org.tframework.test.commons.TestApplicationLauncherFactory;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ExpectInitializationFailure;
import org.tframework.test.commons.annotations.InjectInitializationException;
import org.tframework.test.commons.annotations.SetApplicationName;
import org.tframework.test.commons.annotations.SetCommandLineArguments;
import org.tframework.test.commons.annotations.SetProfiles;
import org.tframework.test.commons.annotations.SetProperties;
import org.tframework.test.commons.annotations.SetRootClass;
import org.tframework.test.commons.appliers.TestConfigAppliersBundle;
import org.tframework.test.commons.appliers.TestConfigAppliersFactory;
import org.tframework.test.commons.populators.TestConfigPopulatorsBundle;
import org.tframework.test.commons.populators.TestConfigPopulatorsFactory;
import org.tframework.test.commons.validators.TestClassValidatorsBundle;
import org.tframework.test.commons.validators.TestClassValidatorsFactory;

/**
 * This is a JUnit 5 extension that allows to easily start TFramework applications. <b>The test class must be marked
 * with {@link Element}, so that it will be scanned, allowing to field inject dependencies</b>. The application will
 * be started before the tests, once. It will be stopped after all tests are completed. It is recommended to use the
 * composed annotations {@link TFrameworkJunit5Test} or {@link IsolatedTFrameworkJunit5Test} which come with some useful configurations.
 *
 * <strong>Configuring the application</strong><br><br>
 * Additional annotations can be used on the test class to specify the details of the started application:
 * <ul>
 *     <li>{@link SetApplicationName} can be used to set a custom application name. By default, the name will be deduced from the test class.</li>
 *     <li>{@link SetRootClass} can be used to set an application root class. By default, the test class will be the root class.</li>
 *     <li>{@link SetCommandLineArguments} can be used to specify arguments passed to the application. By default nothing will be passed.</li>
 *     <li>{@link SetProfiles} can be used to set profiles.</li>
 *     <li>{@link SetProperties} can be used to control general properties.</li>
 *     <li>{@link ElementSettings} can be used to control element related settings, such as what to scan.</li>
 * </ul>
 * A {@link ComposedAnnotationScanner} will be used to pick up these annotations,
 * so it is possible to use composed meta annotations that combine {@link ExtendWith} and
 * the other annotations described above.
 *
 * <strong>Using the application</strong><br><br>
 * There are some ways to get the launched {@link Application} object or any other element or property from the
 * application.
 * <ul>
 *     <li>They can be field injected into the test class, because the test class is an element.</li>
 *     <li>
 *         They can be added as a parameter to JUnit methods such as {@link BeforeEach} and {@link Test}.
 *         Parameters need to be annotated with {@code @InjectX} annotations such as {@link InjectElement}.
 *     </li>
 * </ul>
 *
 * <strong>Initialization failure</strong><br><br>
 * There are cases where the test expects the application initialization to fail. In these cases:
 * <ul>
 *     <li>Place the {@link ExpectInitializationFailure} annotation.</li>
 *     <li>
 *         The {@link InjectInitializationException} can be used on an {@link Exception} typed test method parameter to inject the
 *         exception that caused the failure, which can be asserted inside the test.
 *     </li>
 * </ul>
 * @see IsolatedTFrameworkJunit5Test
 * @see TFrameworkJunit5Test
 */
@Slf4j
public class TFrameworkExtension implements Extension, BeforeAllCallback, TestInstanceFactory, AfterAllCallback, ParameterResolver {

    private static final String EXTENSION_NAME = "junit5-extension";

    private static final String DECLARED_AS_TEST_PARAMETER = "JUnit 5 test method parameter";

    private final AnnotationScanner annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
    private final InjectAnnotationScanner injectAnnotationScanner = new InjectAnnotationScanner(annotationScanner);

    private final TestConfig.TestConfigBuilder configBuilder;
    private final TestClassValidatorsBundle validators;
    private final TestConfigPopulatorsBundle populators;
    private final TestConfigAppliersBundle appliers;
    private final TestApplicationLauncher launcher;
    private LaunchResult launchResult;

    public TFrameworkExtension(TestConfig.TestConfigBuilder configBuilder) {
        this.configBuilder = configBuilder;
        this.validators = TestClassValidatorsFactory.createTestClassValidators();
        this.populators = TestConfigPopulatorsFactory.createTestConfigPopulators();
        this.appliers = TestConfigAppliersFactory.createTestConfigAppliers(EXTENSION_NAME);
        this.launcher = TestApplicationLauncherFactory.createTestApplicationLauncher();
    }

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        var testClass = extensionContext.getRequiredTestClass();
        validators.applyAllValidations(testClass);

        populators.populateWithAll(configBuilder, testClass);
        TestConfig testConfig = configBuilder.build();
        appliers.applyAll(testConfig);

        launchResult = launcher.launchTestApplication(testConfig);
    }

    @Override
    public Object createTestInstance(TestInstanceFactoryContext testInstanceFactoryContext, ExtensionContext extensionContext) throws TestInstantiationException {
        if(launchResult instanceof SuccessfulLaunchResult successfulLaunchResult) {
            var testClassElementContext = successfulLaunchResult.application().getElementsContainer()
                    .getElementContext(extensionContext.getRequiredTestClass());
            log.debug("Found the test class element context '{}', requesting test instance...", testClassElementContext.getName());
            return testClassElementContext.requestInstance();
        } else {
            try {
                log.debug("Application initialization failed, so instance will be constructed normally");
                return testInstanceFactoryContext.getTestClass().getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new TestInstantiationException("Failed to create test instance", e);
            }
        }
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        if(launchResult instanceof SuccessfulLaunchResult successfulLaunchResult) {
            log.debug("Shutting down the '{}' application after all tests", successfulLaunchResult.application().getName());
            TFramework.stop(successfulLaunchResult.application());
        }
        appliers.close();
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        if(parameterContext.getDeclaringExecutable() instanceof Constructor<?>) {
            log.error("Injecting the application into the test instance constructor is not supported, because " +
                    "the application is not yet started.");
            return false;
        }
        if(launchResult.successfulLaunch()) {
            return injectAnnotationScanner.hasAnyInjectAnnotations(parameterContext.getParameter());
        } else {
            return annotationScanner.hasAnnotation(parameterContext.getParameter(), InjectInitializationException.class);
        }
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return switch (launchResult) {
            case SuccessfulLaunchResult successfulLaunchResult -> {
                var definition = DependencyDefinition.fromParameter(parameterContext.getParameter());
                return successfulLaunchResult.dependencyResolver().resolveDependency(
                        definition,
                        testClassElementContext,
                        ElementDependencyGraph.empty(),
                        DECLARED_AS_TEST_PARAMETER
                );
            }
            case FailedLaunchResult failedLaunchResult -> failedLaunchResult.initializationException();
        };
    }

}
