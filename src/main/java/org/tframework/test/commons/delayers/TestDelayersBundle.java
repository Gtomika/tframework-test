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
package org.tframework.test.commons.delayers;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.test.commons.SuccessfulLaunchResult;

/**
 * Aggregates {@link TestDelayer}s and checks whether the test should be delayed.
 */
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TestDelayersBundle {

    /**
     * One increment of delay in milliseconds.
     */
    public static final int DEFAULT_DELAY_INTERVAL = 50;

    private final int delayMillis;
    private final List<TestDelayer> testDelayers;

    /**
     * Delays the test execution if any of the delayers decide to delay it.
     * @param launchResult {@link SuccessfulLaunchResult} describing the launch.
     * @param testClass The test class.
     * @return How many increments of delay were applied.
     */
    public int delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass) {
        boolean delay;
        int delayCount = 0;
        do {
            delay = testDelayers.stream().anyMatch(delayer -> delayer.delayTest(launchResult, testClass));
            if (delay) {
                try {
                    log.debug("At least one delayer decided to delay the test execution, delaying for {} ms", delayMillis);
                    Thread.sleep(delayMillis);
                    delayCount++;
                } catch (InterruptedException e) {
                    log.error("Interrupted while delaying test execution", e);
                }
            }
        } while (delay);
        return delayCount;
    }

    /**
     * Creates a new {@link TestDelayersBundle} with the given delayers.
     */
    public static TestDelayersBundle of(List<TestDelayer> testDelayers) {
        return new TestDelayersBundle(DEFAULT_DELAY_INTERVAL, testDelayers);
    }

}
