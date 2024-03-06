/* Licensed under Apache-2.0 2024. */
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
