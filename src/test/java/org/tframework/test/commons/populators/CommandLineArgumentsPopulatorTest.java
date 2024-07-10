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

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetCommandLineArguments;

@SetCommandLineArguments({"a", "b"})
@ExtendWith(MockitoExtension.class)
public class CommandLineArgumentsPopulatorTest {

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private CommandLineArgumentsPopulator populator;

    @Test
    public void shouldPopulateArguments_keepingExistingValues_whenAnnotationPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetCommandLineArguments.class))
                .thenReturn(Optional.of(this.getClass().getAnnotation(SetCommandLineArguments.class)));

        var configBuilder = TestConfig.builder()
                .commandLineArguments(List.of("c", "d"));
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(List.of("a", "b", "c", "d"), configBuilder.build().commandLineArguments());
    }

    @Test
    public void shouldPopulateArguments_keepingExistingValues_whenAnnotationNotPresent() {
        when(annotationScanner.scanOneStrict(this.getClass(), SetCommandLineArguments.class))
                .thenReturn(Optional.empty());

        var configBuilder = TestConfig.builder()
                .commandLineArguments(List.of("c", "d"));
        populator.populateConfig(configBuilder, this.getClass());

        assertEquals(List.of("c", "d"), configBuilder.build().commandLineArguments());
    }

}
