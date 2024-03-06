/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PropertiesTestApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        if(testConfig.properties() != null) {
            testConfig.properties().forEach(rawProperty -> {
                log.debug("Setting property for the test application: '{}'", rawProperty);
                systemPropertyHelper.setRawFrameworkPropertyIntoSystemProperties(rawProperty);
            });
        }
    }
}
