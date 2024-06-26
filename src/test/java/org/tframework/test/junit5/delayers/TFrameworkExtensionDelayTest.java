package org.tframework.test.junit5.delayers;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.events.CoreEvents;
import org.tframework.core.events.EventManager;
import org.tframework.core.events.annotations.Subscribe;
import org.tframework.test.commons.annotations.EventDelay;
import org.tframework.test.junit5.IsolatedTFrameworkTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
@EventDelay(topic = TFrameworkExtensionDelayTest.IMPORTANT_TOPIC)
@IsolatedTFrameworkTest
public class TFrameworkExtensionDelayTest {

    public static final String IMPORTANT_TOPIC = "Important topic";

    @InjectElement
    private EventManager eventManager;

    private boolean importantWorkDone = false;

    @Subscribe(CoreEvents.APPLICATION_INITIALIZED)
    public void onApplicationInitialized(Application application) throws Exception {
        log.info("Application initialized, starting very important work...");
        Thread.sleep(100);
        importantWorkDone = true;
        log.info("Very important work done, sending event!");
        eventManager.publish(IMPORTANT_TOPIC, "Very important event!");
    }

    @Test
    public void shouldRunThisTest_whenImportantWorkIsDone() {
        assertTrue(importantWorkDone);
    }
}
