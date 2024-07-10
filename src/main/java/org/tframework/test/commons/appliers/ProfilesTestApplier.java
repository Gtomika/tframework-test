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
