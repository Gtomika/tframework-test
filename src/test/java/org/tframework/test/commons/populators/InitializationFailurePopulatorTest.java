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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ExpectInitializationFailure;

@ExpectInitializationFailure
@ExtendWith(MockitoExtension.class)
public class InitializationFailurePopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private InitializationFailurePopulator populator;

    @Test
    public void shouldPopulateConfig_overridingExistingValue_whenAnnotationPresent() {
        when(annotationScanner.hasAnnotation(this.getClass(), ExpectInitializationFailure.class)).thenReturn(true);

        var configBuilder = TestConfig.builder()
                .expectInitializationFailure(false);
        populator.populateConfig(configBuilder, this.getClass());

        assertTrue(configBuilder.build().expectInitializationFailure());
    }

    @Test
    public void shouldPopulateConfig_keepingExistingValue_whenAnnotationNotPresent() {
        when(annotationScanner.hasAnnotation(this.getClass(), ExpectInitializationFailure.class)).thenReturn(false);

        var configBuilder = TestConfig.builder()
                .expectInitializationFailure(true);
        populator.populateConfig(configBuilder, this.getClass());

        assertTrue(configBuilder.build().expectInitializationFailure());
    }
}
