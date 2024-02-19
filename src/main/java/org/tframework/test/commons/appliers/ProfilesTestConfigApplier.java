package org.tframework.test.commons.appliers;

import lombok.extern.slf4j.Slf4j;
import org.tframework.core.profiles.scanners.SystemPropertyProfileScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
public class ProfilesTestConfigApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;
    private final String id;

    ProfilesTestConfigApplier(SystemPropertyHelper systemPropertyHelper, String id) {
        this.systemPropertyHelper = systemPropertyHelper;
        this.id = id;
    }

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        if(!testConfig.profiles().isEmpty()) {
            log.debug("The following profiles will be set for the test application {}", testConfig.profiles());

            String profilesJoined = String.join(",", testConfig.profiles());

            String profilesSystemProperty = SystemPropertyProfileScanner.PROFILES_SYSTEM_PROPERTY + "." + id;
            systemPropertyHelper.setIntoSystemProperties(profilesSystemProperty, profilesJoined);
        } else {
            log.debug("No additional profiles will be set for the test application");
        }
    }
}
