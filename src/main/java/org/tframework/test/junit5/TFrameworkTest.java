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
package org.tframework.test.junit5;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tframework.core.elements.ElementScope;
import org.tframework.core.elements.annotations.Element;
import org.tframework.test.commons.annotations.ElementSettings;
import org.tframework.test.commons.annotations.RootClassSettings;

/**
 * A composed annotation of {@link TFrameworkExtension} and configurations, that can be used to start a full-fledged
 * TFramework application for the tests. The root class will be looked up from the classpath, it must be
 * annotated with {@link org.tframework.core.TFrameworkRootClass}. All element scanning is enabled.
 * <br><br>
 * <b>When using this annotation, the test class must not also be the root class of the application.</b>
 */
@ExtendWith(TFrameworkExtension.class)
@RootClassSettings(
        useTestClassAsRoot = false,
        findRootClassOnClasspath = true
)
@ElementSettings(
        rootScanningEnabled = true,
        rootHierarchyScanningEnabled = true
)
@Element(scope = ElementScope.SINGLETON)
@Retention(RetentionPolicy.RUNTIME)
public @interface TFrameworkTest {
}
