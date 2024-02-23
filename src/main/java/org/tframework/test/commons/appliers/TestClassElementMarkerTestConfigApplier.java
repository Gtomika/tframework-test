/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import java.util.List;
import org.tframework.core.elements.scanner.ClassesElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

public class TestClassElementMarkerTestConfigApplier implements TestConfigApplier {

    static final String TEST_CLASS_NOT_PROVIDED_MESSAGE = "The test class must be provided in the configuration";

    private final SystemPropertyHelper systemPropertyHelper;
    private final String extensionName;

    TestClassElementMarkerTestConfigApplier(SystemPropertyHelper systemPropertyHelper, String extensionName) {
        this.systemPropertyHelper = systemPropertyHelper;
        this.extensionName = extensionName;
    }

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        if(testConfig.testClass() == null) {
            throw new IllegalStateException(TEST_CLASS_NOT_PROVIDED_MESSAGE);
        }
        String scanTestClassProperty = ClassesElementClassScanner.SCAN_CLASSES_PROPERTY + "-" + extensionName + "-test-class";
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties(
                scanTestClassProperty,
                List.of(testConfig.testClass().getName())
        );
    }

}
