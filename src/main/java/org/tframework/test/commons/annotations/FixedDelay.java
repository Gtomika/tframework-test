/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.tframework.test.commons.delayers.TestDelayersBundle;

/**
 * Can be placed on TFramework test classes to delay the test execution by a fixed amount of time.
 * This delay will be applied <b>after</b> the framework is initialized. You can only have
 * one of this annotation on the test class. Usually it is better to use
 * {@link EventDelay} instead, as it allows for more fine-grained control over the delay.
 * <p><br>
 * Please note that delaying happens in iterations, so if you specify a small delay, the actual delay
 * may be larger than the specified one. Due to this, the minimum delay is
 * {@value TestDelayersBundle#DEFAULT_DELAY_INTERVAL} milliseconds.
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
