/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.profiles.scanners.SystemPropertyProfileScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ProfilesTestApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;
    private final String extensionName;

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
