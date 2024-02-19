package org.tframework.test.commons.appliers;

import lombok.extern.slf4j.Slf4j;
import org.tframework.core.elements.scanner.InternalElementClassScanner;
import org.tframework.core.elements.scanner.RootElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@Slf4j
public class ElementSettingsConfigApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;

    ElementSettingsConfigApplier(SystemPropertyHelper systemPropertyHelper) {
        this.systemPropertyHelper = systemPropertyHelper;
    }

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

        log.debug("Will internal scanning be enabled for the test application? {}", testConfig.internalScanningEnabled());
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties(
                InternalElementClassScanner.TFRAMEWORK_INTERNAL_SCAN_ENABLED_PROPERTY,
                String.valueOf(testConfig.internalScanningEnabled())
        );
    }
}
