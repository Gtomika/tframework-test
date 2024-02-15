/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.tframework.test.junit5.TFrameworkExtension;

/**
 * Used in conjunction with {@link TFrameworkExtension} to specify element related settings
 * or additional classes/packages that should be scanned during tests.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SetElements {

    /**
     * Control if the root scanning should be enabled.
     * @see org.tframework.core.elements.scanner.RootElementClassScanner
     */
    boolean rootScanningEnabled() default true;

    /**
     * Control if the root hierarchy scanning should be enabled.
     * @see org.tframework.core.elements.scanner.RootElementClassScanner
     */
    boolean rootHierarchyScanningEnabled() default true;

    /**
     * Control if the internal scanning should be enabled.
     * @see org.tframework.core.elements.scanner.InternalElementClassScanner
     */
    boolean internalScanningEnabled() default true;

    /**
     * Set additional packages to scanned for elements.
     * @see org.tframework.core.elements.scanner.PackagesElementClassScanner
     */
    String[] scanAdditionalPackages() default {};

    /**
     * Set additional classes to be scanned for elements.
     * @see org.tframework.core.elements.scanner.ClassesElementClassScanner
     */
    String[] scanAdditionalClasses() default {};

}
