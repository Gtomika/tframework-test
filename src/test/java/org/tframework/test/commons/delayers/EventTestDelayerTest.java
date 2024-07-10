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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.Application;
import org.tframework.core.elements.ElementsContainer;
import org.tframework.core.events.EventManager;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.annotations.EventDelay;

@EventDelay(topic = EventTestDelayerTest.TOPIC)
@ExtendWith(MockitoExtension.class)
public class EventTestDelayerTest {

    public static final String TOPIC = "topic";

    private final Class<?> testClass = EventTestDelayerTest.class;

    private Consumer<Object> subscription;
    private SuccessfulLaunchResult launchResult;

    @Mock
    private AnnotationScanner annotationScanner;

    @Mock
    private ElementsContainer elementsContainer;

    @Mock
    private EventManager eventManager;

    private EventTestDelayer eventTestDelayer;

    @Test
    public void shouldNotDelayTest_whenEventDelayAnnotationIsNotPresent() {
        createTestDelayer(() -> when(annotationScanner.scanOneStrict(testClass, EventDelay.class))
                .thenReturn(Optional.empty()));
        boolean delay = eventTestDelayer.delayTest(launchResult, testClass);
        assertFalse(delay);
    }

    @Test
    public void shouldNotDelayTest_whenEventDelayAnnotationIsPresent_eventReceived() {
        createTestDelayer(() -> {
            when(annotationScanner.scanOneStrict(testClass, EventDelay.class))
                    .thenReturn(Optional.of(testClass.getAnnotation(EventDelay.class)));
            when(elementsContainer.getElement(EventManager.class)).thenReturn(eventManager);
            when(eventManager.subscribe(eq(TOPIC), any())).thenAnswer(invocation -> {
                subscription = invocation.getArgument(1);
                return UUID.randomUUID();
            });
        });

        //at this point the event delayer is created, and it subscribed to the topic
        //simulate the event being received
        subscription.accept(new Object());

        boolean delay = eventTestDelayer.delayTest(launchResult, testClass);
        assertFalse(delay);
    }

    @Test
    public void shouldDelayTest_whenEventDelayAnnotationIsPresent_eventNotReceived() {
        createTestDelayer(() -> {
            when(annotationScanner.scanOneStrict(testClass, EventDelay.class))
                    .thenReturn(Optional.of(testClass.getAnnotation(EventDelay.class)));
            when(elementsContainer.getElement(EventManager.class)).thenReturn(eventManager);
        });

        boolean delay = eventTestDelayer.delayTest(launchResult, testClass);
        assertTrue(delay);
    }

    private void createTestDelayer(Runnable setupMocks) {
        setupMocks.run();

        var application = Application.builder()
                .elementsContainer(elementsContainer)
                .build();

        launchResult = SuccessfulLaunchResult.builder()
                .application(application)
                .build();
        eventTestDelayer = new EventTestDelayer(annotationScanner, launchResult, testClass);
    }
}
