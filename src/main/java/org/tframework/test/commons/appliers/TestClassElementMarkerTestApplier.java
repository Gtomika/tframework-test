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

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.elements.scanner.ClassesElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class TestClassElementMarkerTestApplier implements TestConfigApplier {

    static final String TEST_CLASS_NOT_PROVIDED_MESSAGE = "The test class must be provided in the configuration";

    private final SystemPropertyHelper systemPropertyHelper;
    private final String extensionName;

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
