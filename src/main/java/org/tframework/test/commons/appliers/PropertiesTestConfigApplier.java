/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import lombok.extern.slf4j.Slf4j;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
public class PropertiesTestConfigApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;

    PropertiesTestConfigApplier(SystemPropertyHelper systemPropertyHelper) {
        this.systemPropertyHelper = systemPropertyHelper;
    }

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        testConfig.properties().forEach(rawProperty -> {
            log.debug("Setting property for the test application: '{}'", rawProperty);
            systemPropertyHelper.setRawFrameworkPropertyIntoSystemProperties(rawProperty);
        });
    }
}
