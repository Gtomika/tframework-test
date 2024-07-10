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
