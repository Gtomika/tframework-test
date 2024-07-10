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
package org.tframework.test.commons.appliers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.tframework.test.commons.TestConfig;

public class BeforeFrameworkInitializationApplierTest {

    private static String value;

    private final BeforeFrameworkInitializationApplier applier = new BeforeFrameworkInitializationApplier();

    @Test
    public void shouldApplyBeforeFrameworkRunnables() {
        assertNull(value);

        var config = TestConfig.builder()
                .beforeFrameworkCallbacks(List.of(new ValueInitializerCallback()))
                .build();

        applier.applyTestConfig(config);

        assertNotNull(value);
    }

    static class ValueInitializerCallback implements Runnable {

        @Override
        public void run() {
            value = "Initialized";
        }
    }

}
