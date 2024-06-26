/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.delayers;

import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.events.EventManager;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.annotations.EventDelay;

/**
 * A {@link TestDelayer} that scans for {@link EventDelay} annotation on test classes and
 * delays the test execution accordingly.
 */
@Slf4j
public class EventTestDelayer extends TestDelayer {

    private UUID subscriptionId;
    private EventManager eventManager;
    private boolean eventReceived;
    private Optional<EventDelay> fixedDelayAnnotation;

    EventTestDelayer(
            AnnotationScanner annotationScanner,
            SuccessfulLaunchResult launchResult,
            Class<?> testClass
    ) {
        super(annotationScanner, launchResult, testClass);
    }

    @Override
    protected void init() {
        fixedDelayAnnotation = annotationScanner.scanOneStrict(testClass, EventDelay.class);
        eventReceived = false;

        fixedDelayAnnotation.ifPresent(annotation -> {
            String topic = annotation.topic();
            log.info("Subscribing to event topic '{}' for test class: {}. Will delay until event is received.",
                    topic, testClass.getName());

            eventManager = launchResult.application().getElementsContainer().getElement(EventManager.class);
            subscriptionId = eventManager.subscribe(topic, payload -> {
                eventReceived = true;
                log.info("Received event on topic '{}' for test class: {}. WIll no longer delay.",
                        topic, testClass.getName());
            });
        });
    }

    @Override
    public boolean delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass) {
        if(eventReceived) {
            //guaranteed to be present here
            String topic = fixedDelayAnnotation.get().topic();
            log.debug("Event on topic '{}' received, not delaying.", topic);
            eventManager.unsubscribe(subscriptionId);
            return false;
        } else {
            if(fixedDelayAnnotation.isPresent()) {
                String topic = fixedDelayAnnotation.get().topic();
                log.debug("Event on topic '{}' not received yet, delaying.", topic);
                return true;
            } else {
                log.debug("No @EventDelay annotation found, not delaying.");
                return false;
            }
        }
    }
}
