/* Licensed under Apache-2.0 2024. */
package org.tframework.test.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to indicate that
 * the framework initialization is expected to fail. This may be used when testing initialization
 * failures.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectInitializationFailure {
}
