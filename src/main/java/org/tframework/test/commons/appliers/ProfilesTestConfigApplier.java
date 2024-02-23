/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import lombok.extern.slf4j.Slf4j;
import org.tframework.core.profiles.scanners.SystemPropertyProfileScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
public class ProfilesTestConfigApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;
    private final String extensionName;

    ProfilesTestConfigApplier(SystemPropertyHelper systemPropertyHelper, String extensionName) {
        this.systemPropertyHelper = systemPropertyHelper;
        this.extensionName = extensionName;
    }

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        if(testConfig.profiles() != null && !testConfig.profiles().isEmpty()) {
            log.debug("The following profiles will be set for the test application {}", testConfig.profiles());

            String profilesJoined = String.join(",", testConfig.profiles());

            String profilesSystemProperty = SystemPropertyProfileScanner.PROFILES_SYSTEM_PROPERTY + "." + extensionName;
            systemPropertyHelper.setIntoSystemProperties(profilesSystemProperty, profilesJoined);
        } else {
            log.debug("No additional profiles will be set for the test application");
        }
    }
}
