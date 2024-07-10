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
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to specify the application's root class. Note that only
 * one attribute of this annotation can be used to specify the root class. For example if {@link #useTestClassAsRoot()}
 * is set to true, then {@link #findRootClassOnClasspath()} should be false, and {@link #rootClass()} should be
 * left unspecified.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RootClassSettings {

    Class<?> ROOT_CLASS_NOT_DIRECTLY_SPECIFIED = Void.class;

    /**
     * Use the test class as the root of the TFramework application.
     */
    boolean useTestClassAsRoot() default true;

    /**
     * Search the classpath for {@link org.tframework.core.TFrameworkRootClass} annotated class
     * and use that as the application root.
     */
    boolean findRootClassOnClasspath() default false;

    /**
     * Directly specify the root class.
     */
    Class<?> rootClass() default Void.class; //compiler does not allow to use variable here

}
