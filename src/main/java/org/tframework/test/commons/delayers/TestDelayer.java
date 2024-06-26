package org.tframework.test.commons.delayers;

import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;

/**
 * A test delayer decides whether to delay the test execution based on the launch result
 * and the test class. Note that only successful launches are passed to the delayer, failed
 * launches are not delayed.
 */
public abstract class TestDelayer {

    protected final AnnotationScanner annotationScanner;
    protected final SuccessfulLaunchResult launchResult;
    protected final Class<?> testClass;

    protected TestDelayer(
            AnnotationScanner annotationScanner,
            SuccessfulLaunchResult launchResult,
            Class<?> testClass
    ) {
        this.annotationScanner = annotationScanner;
        this.launchResult = launchResult;
        this.testClass = testClass;
        init();
    }

    /**
     * Initializes the delayer. All fields are already set.
     */
    protected abstract void init();

    /**
     * Decides whether to delay the test execution.
     * @param launchResult {@link SuccessfulLaunchResult} describing the launch.
     * @param testClass The test class.
     * @return True if the test should be delayed, false otherwise.
     */
    public abstract boolean delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass);

}
