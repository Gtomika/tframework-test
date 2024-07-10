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
