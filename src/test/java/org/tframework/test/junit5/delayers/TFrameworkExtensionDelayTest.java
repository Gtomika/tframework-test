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
package org.tframework.test.junit5.delayers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.events.CoreEvents;
import org.tframework.core.events.EventManager;
import org.tframework.core.events.annotations.Subscribe;
import org.tframework.test.commons.annotations.EventDelay;
import org.tframework.test.junit5.IsolatedTFrameworkTest;

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
