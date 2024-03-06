/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with TFramework tests, to specify {@link Runnable}s to
 * execute before the framework initialized. This annotation may be useful because {@link org.junit.jupiter.api.BeforeAll}
 * methods are invoked <b>after</b> the framework is initialized, and thus, cannot affect the initialization
 * process.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeFrameworkInitialization {

    /**
     * Specifies the {@link Runnable} class to use. This class must have a public, no args constructor.
     */
    Class<? extends Runnable> callback();

}
