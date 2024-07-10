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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tframework.core.TFrameworkRootClass;
import org.tframework.test.commons.annotations.ElementSettings;
import org.tframework.test.commons.annotations.RootClassSettings;

/**
 * A composed annotation of {@link TFrameworkExtension} and other configurations: this allows to
 * launch a small and contained TFramework application withing a given test class.
 * <p><br>
 * The root class of this application will be the test class. Elements inside the test class
 * will be scanned. Internal elements will be scanned as well. However, no other packages
 * and classes will be checked for elements.
 */
@ExtendWith(TFrameworkExtension.class)
@RootClassSettings(
        useTestClassAsRoot = true,
        findRootClassOnClasspath = false
)
@ElementSettings(
        rootScanningEnabled = true, //only the root class (which is the test class) will be scanned
        rootHierarchyScanningEnabled = false
)
@TFrameworkRootClass
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface IsolatedTFrameworkTest {
}
