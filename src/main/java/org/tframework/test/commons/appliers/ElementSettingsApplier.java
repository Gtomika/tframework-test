/* Licensed under Apache-2.0 2024. */
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
