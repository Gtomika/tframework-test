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
import org.tframework.core.events.CoreEvents;

/**
 * Can be placed on TFramework test classes to delay the test execution until an event occurs.
 * The first event that is detected will cause the delay to end. You can only have
 * one of this annotation on the test class. This delay will be applied <b>after</b>
 * the framework is initialized.
 * <p><br>
 * Suppose that the application under test has to perform some lengthy operation before it is ready to be tested.
 * In this case, the test can be delayed until the application sends an event that indicates that it is ready.
 * <p><br>
 * Notes:
 * <ul>
 *     <li>If you never publish an event on {@link #topic()}, the tests will be delayed indefinitely.</li>
 *     <li>If you publish multiple events on {@link #topic()}, the first one will cause the delay to end.</li>
 *     <li>
 *         <b>Do not</b> use this annotation for {@link CoreEvents}! These may be fired before the framework is
 *         initialized, which can cause the event delayer to miss them, and wait indefinitely.
 *     </li>
 * </ul>
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventDelay {

    /**
     * The topic of the event to wait for.
     */
    String topic();

}
