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
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ElementSettings;

@ElementSettings(
        rootScanningEnabled = false,
        rootHierarchyScanningEnabled = false
)
@ExtendWith(MockitoExtension.class)
public class ElementSettingsPopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private ElementSettingsPopulator populator;

    @Test
    public void shouldPopulateElementSettings_overridingExistingValues_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), ElementSettings.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(ElementSettings.class)));

        var configBuilder = TestConfig.builder()
                .rootScanningEnabled(true)
                .rootHierarchyScanningEnabled(true);
        populator.populateConfig(configBuilder, this.getClass());

        var config = configBuilder.build();
        assertFalse(config.rootScanningEnabled());
        assertFalse(config.rootHierarchyScanningEnabled());
    }

    @Test
    public void shouldPopulateElementSettings_keepingExistingValues_whenAnnotationNotPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), ElementSettings.class))
                .thenReturn(Optional.empty());

        var configBuilder = TestConfig.builder()
                .rootScanningEnabled(true)
                .rootHierarchyScanningEnabled(true);
        populator.populateConfig(configBuilder, this.getClass());

        var config = configBuilder.build();
        assertTrue(config.rootScanningEnabled());
        assertTrue(config.rootHierarchyScanningEnabled());
    }
}
