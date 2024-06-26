package org.tframework.test.commons.delayers;

import org.tframework.test.commons.SuccessfulLaunchResult;

/**
 * A test delayer decides whether to delay the test execution based on the launch result
 * and the test class. Note that only successful launches are passed to the delayer, failed
 * launches are not delayed.
 */
public interface TestDelayer {

    /**
     * Decides whether to delay the test execution.
     * @param launchResult {@link SuccessfulLaunchResult} describing the launch.
     * @param testClass The test class.
     * @return True if the test should be delayed, false otherwise.
     */
    boolean delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass);

}
