/* Licensed under Apache-2.0 2024. */
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
public @interface SetRootClass {

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
