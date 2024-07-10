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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetApplicationName;

@SetApplicationName(ApplicationNamePopulatorTest.TEST_NAME)
@ExtendWith(MockitoExtension.class)
public class ApplicationNamePopulatorTest {

    public static final String TEST_NAME = "test-app";

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private ApplicationNamePopulator populator;

    @Test
    public void shouldPopulateConfig_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(SetApplicationName.class)));

        var configBuilder = TestConfig.builder();
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(TEST_NAME, configBuilder.build().applicationName());
    }

    @Test
    public void shouldPopulateConfig_notOverridingExistingValue_whenAnnotationNotPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.empty());

        var configBuilder = TestConfig.builder()
                .applicationName("other-name");
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals("other-name", configBuilder.build().applicationName());
    }

    @Test
    public void shouldPopulateConfig_overridingExistingValue_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetApplicationName.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(SetApplicationName.class)));

        var configBuilder = TestConfig.builder()
                .applicationName("other-name");
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(TEST_NAME, configBuilder.build().applicationName());
    }

}
