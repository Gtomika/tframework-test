package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Can be placed on TFramework test classes to delay the test execution by a fixed amount of time.
 * This delay will be applied <b>after</b> the framework is initialized. Usually it is better to use
 * {@link EventDelay} instead, as it allows for more fine-grained control over the delay.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FixedDelay {

    /**
     * The amount of time to delay the test execution by, in milliseconds.
     */
    int millis();

}
