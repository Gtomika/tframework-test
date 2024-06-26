package org.tframework.test.commons.delayers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.test.commons.SuccessfulLaunchResult;

import java.util.List;

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
