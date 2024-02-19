/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

import java.util.List;
import java.util.Set;
import lombok.Builder;
import org.tframework.core.TFrameworkRootClass;
import org.tframework.core.properties.parsers.PropertyParsingUtils;
import org.tframework.test.commons.annotations.InjectInitializationException;

/**
 * All the configurations that can be used to customize the frameworks
 * behaviour during tests.
 * @param expectInitializationFailure If the test application initialization is expected to fail. Defaults to false.
 *          Set to true if the test needs the framework to throw an exception during
 *          initialization, which can then be used with {@link InjectInitializationException}
 * @param applicationName Configures the name of the test application. By default, the name will be
 *          created from the test class.
 * @param commandLineArguments Configures the command line arguments to be passed to the test application. By default,
 *          nothing will be passed.
 * @param rootScanningEnabled Configures of the root class should be scanned for elements. True by default.
 * @param rootHierarchyScanningEnabled Configures of the root class hierarchy should be scanned for elements. True by default.
 * @param internalScanningEnabled Configures whether the internal packages should be scanned for elements. True by default.
 * @param profiles Configures the profiles that should be set for the test application.
 * @param properties Configures the properties that should be set for the test application. Properties should be provided
 *          as raw strings, where the name and value are separated by
 *          {@value PropertyParsingUtils#PROPERTY_NAME_VALUE_SEPARATOR}
 * @param useTestClassAsRoot Configure if the test application's root class should be the test class. True by default.
 *          Mutually exclusive with {@link #findRootClassOnClasspath} and {@link #rootClass}.
 * @param findRootClassOnClasspath Configure if the test application's root class should be scanned on the classpath. False by default.
 *          If set to true, there must be exactly one class on the classpath annotated with {@link TFrameworkRootClass}.
 *          Mutually exclusive with {@link #useTestClassAsRoot} and {@link #rootClass}
 * @param rootClass Configure the test application's root class explicitly. Not provided by default.
 *          Mutually exclusive with {@link #useTestClassAsRoot} and {@link #findRootClassOnClasspath}.
 * @param testClass The class from which the test is running.
 *
 */
@Builder
public record TestConfig(
         boolean expectInitializationFailure,
         String applicationName,
         List<String> commandLineArguments,
         boolean rootScanningEnabled,
         boolean rootHierarchyScanningEnabled,
         boolean internalScanningEnabled,
         Set<String> profiles,
         Set<String> properties,
         boolean useTestClassAsRoot,
         boolean findRootClassOnClasspath,
         Class<?> rootClass,
         Class<?> testClass
) {

}
