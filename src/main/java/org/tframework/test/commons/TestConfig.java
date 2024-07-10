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
import java.util.Set;
import lombok.Builder;
import org.tframework.core.TFrameworkRootClass;
import org.tframework.core.elements.PreConstructedElementData;
import org.tframework.core.properties.parsers.PropertyParsingUtils;
import org.tframework.test.commons.annotations.InjectInitializationException;
import org.tframework.test.junit5.TFrameworkExtension;

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
 * @param beforeFrameworkCallbacks A list of {@link Runnable}s that should be invoked before the framework is started.
 * @param preConstructedElements A set of {@link PreConstructedElementData} about existing objects that should
 *                               be added as elements to the test application.
 *
 */
@Builder(toBuilder = true)
public record TestConfig(
         boolean expectInitializationFailure,
         String applicationName,
         List<String> commandLineArguments,
         boolean rootScanningEnabled,
         boolean rootHierarchyScanningEnabled,
         Set<String> profiles,
         Set<String> properties,
         boolean useTestClassAsRoot,
         boolean findRootClassOnClasspath,
         Class<?> rootClass,
         Class<?> testClass,
         List<? extends Runnable> beforeFrameworkCallbacks,
         Set<PreConstructedElementData> preConstructedElements
) {

    /**
     * Create a {@link TFrameworkExtension} Junit5 extension using this test config.
     * @return The created extension.
     */
    public TFrameworkExtension toJunit5Extension() {
        return new TFrameworkExtension(this.toBuilder());
    }

}
