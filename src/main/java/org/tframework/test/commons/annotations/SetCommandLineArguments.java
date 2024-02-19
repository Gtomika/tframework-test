/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to simulate command line arguments passed to
 * the application.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SetCommandLineArguments {

    /**
     * The "command line arguments" that will be passed to the application.
     */
    String[] value();

}
