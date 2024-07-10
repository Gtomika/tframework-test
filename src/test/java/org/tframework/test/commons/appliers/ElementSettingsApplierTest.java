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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.elements.scanner.RootElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@ExtendWith(MockitoExtension.class)
public class ElementSettingsApplierTest {

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    @InjectMocks
    private ElementSettingsApplier applier;

    @Test
    public void shouldApplyConfig() {
        var config = TestConfig.builder()
                .rootScanningEnabled(true)
                .rootHierarchyScanningEnabled(true)
                .build();

        applier.applyTestConfig(config);

        verify(systemPropertyHelper, times(1))
                .setFrameworkPropertyIntoSystemProperties(RootElementClassScanner.ROOT_SCANNING_ENABLED_PROPERTY, "true");
        verify(systemPropertyHelper, times(1))
                .setFrameworkPropertyIntoSystemProperties(RootElementClassScanner.ROOT_HIERARCHY_SCANNING_ENABLED_PROPERTY, "true");
    }
}
