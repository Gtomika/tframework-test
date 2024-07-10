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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.elements.scanner.ClassesElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@ExtendWith(MockitoExtension.class)
public class TestClassElementMarkerApplierTest {

    private static final String EXTENSION_NAME = "test";

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    private TestClassElementMarkerTestApplier applier;

    @BeforeEach
    void setUp() {
        applier = new TestClassElementMarkerTestApplier(systemPropertyHelper, EXTENSION_NAME);
    }

    @Test
    public void shouldApplyConfig_whenTestClassIsProvided() {
        var config = TestConfig.builder()
                .testClass(this.getClass())
                .build();
        applier.applyTestConfig(config);

        String expectedPropertyName = ClassesElementClassScanner.SCAN_CLASSES_PROPERTY + "-" + EXTENSION_NAME + "-test-class";
        var expectedClassNames = List.of(this.getClass().getName());

        verify(systemPropertyHelper, times(1))
                .setFrameworkPropertyIntoSystemProperties(expectedPropertyName, expectedClassNames);
    }

    @Test
    public void shouldThrowException_whenTestClassIsNotProvided() {
        var config = TestConfig.builder().build();

        var exception = assertThrows(IllegalStateException.class, () -> applier.applyTestConfig(config));

        assertEquals(
                TestClassElementMarkerTestApplier.TEST_CLASS_NOT_PROVIDED_MESSAGE,
                exception.getMessage()
        );
    }
}
