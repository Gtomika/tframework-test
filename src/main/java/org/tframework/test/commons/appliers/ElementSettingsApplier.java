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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.elements.scanner.RootElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
@RequiredArgsConstructor
public class ElementSettingsApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        log.debug("Will root scanning be enabled for the test application? {}", testConfig.rootScanningEnabled());
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties(
                RootElementClassScanner.ROOT_SCANNING_ENABLED_PROPERTY,
                String.valueOf(testConfig.rootScanningEnabled())
        );

        log.debug("Will root hierarchy scanning be enabled for the test application? {}", testConfig.rootHierarchyScanningEnabled());
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties(
                RootElementClassScanner.ROOT_HIERARCHY_SCANNING_ENABLED_PROPERTY,
                String.valueOf(testConfig.rootHierarchyScanningEnabled())
        );
    }
}
