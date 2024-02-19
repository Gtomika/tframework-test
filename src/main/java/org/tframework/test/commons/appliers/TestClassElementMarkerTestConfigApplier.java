package org.tframework.test.commons.appliers;

import org.tframework.core.elements.scanner.ClassesElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

import java.util.List;

public class TestClassElementMarkerTestConfigApplier implements TestConfigApplier {

    private final SystemPropertyHelper systemPropertyHelper;
    private final String id;

    TestClassElementMarkerTestConfigApplier(SystemPropertyHelper systemPropertyHelper, String id) {
        this.systemPropertyHelper = systemPropertyHelper;
        this.id = id;
    }

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        String scanTestClassProperty = ClassesElementClassScanner.SCAN_CLASSES_PROPERTY + "-" + id + "-test-class";
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties(
                scanTestClassProperty,
                List.of(testConfig.testClass().getName())
        );
    }

}
