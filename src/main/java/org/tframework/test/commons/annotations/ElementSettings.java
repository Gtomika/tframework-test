/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to specify element related settings.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSettings {

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
}
