/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.delayers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.annotations.FixedDelay;

@FixedDelay(millis = FixedTestDelayerTest.DELAY)
@ExtendWith(MockitoExtension.class)
public class FixedTestDelayerTest {

    public static final int DELAY = 10;

    private final SuccessfulLaunchResult launchResult = SuccessfulLaunchResult.builder().build();
    private final Class<?> testClass = FixedTestDelayerTest.class;

    @Mock
    private AnnotationScanner annotationScanner;

    private FixedTestDelayer fixedTestDelayer;

    @Test
    public void shouldNotDelayTest_whenFixedDelayAnnotationIsNotPresent() {
        createTestDelayer(() -> when(annotationScanner.scanOneStrict(testClass, FixedDelay.class)).thenReturn(Optional.empty()));
        boolean delay = fixedTestDelayer.delayTest(launchResult, testClass);
        assertFalse(delay);
    }

    @Test
    public void shouldDelayTest_whenFixedDelayAnnotationIsPresent_notEnoughTimePassed() {
        createTestDelayer(() -> when(annotationScanner.scanOneStrict(testClass, FixedDelay.class)).thenReturn(
                Optional.of(testClass.getAnnotation(FixedDelay.class))));

        boolean delay = fixedTestDelayer.delayTest(launchResult, testClass);
        assertTrue(delay);
    }

    @Test
    public void shouldNotDelayTest_whenFixedDelayAnnotationIsPresent_enoughTimePassed() throws Exception {
        createTestDelayer(() -> when(annotationScanner.scanOneStrict(testClass, FixedDelay.class)).thenReturn(
                Optional.of(testClass.getAnnotation(FixedDelay.class))));

        Thread.sleep(DELAY);

        boolean delay = fixedTestDelayer.delayTest(launchResult, testClass);
        assertFalse(delay);
    }

    private void createTestDelayer(Runnable setupMocks) {
        setupMocks.run();
        fixedTestDelayer = new FixedTestDelayer(annotationScanner, launchResult, testClass);
    }
}
