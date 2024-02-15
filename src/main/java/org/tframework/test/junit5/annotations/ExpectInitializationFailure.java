/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.tframework.test.junit5.TFrameworkExtension;

/**
 * Used in conjunction with {@link TFrameworkExtension} to indicate that
 * the framework initialization is expected to fail. This may be used when testing initialization
 * failures.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpectInitializationFailure {
}
