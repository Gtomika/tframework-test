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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.BeforeFrameworkInitialization;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class BeforeFrameworkInitializationPopulatorTest {

    private static final RuntimeException CAUSE = new RuntimeException("Damn");

    @Mock
    private AnnotationScanner annotationScanner;

    @InjectMocks
    private BeforeFrameworkInitializationPopulator populator;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        var testMethod = testInfo.getTestMethod().get();
        var testClass = testInfo.getTestClass().get();

        when(annotationScanner.scan(testClass, BeforeFrameworkInitialization.class))
                .thenReturn(List.of(testMethod.getAnnotation(BeforeFrameworkInitialization.class)));
    }

    @Test
    @BeforeFrameworkInitialization(callback = ValidCallback.class)
    public void shouldPopulateConfig() {
        var configBuilder = TestConfig.builder();
        populator.populateConfig(configBuilder, this.getClass());

        var callback = configBuilder.build().beforeFrameworkCallbacks().getFirst();
        assertInstanceOf(ValidCallback.class, callback);
    }

    @Test
    @BeforeFrameworkInitialization(callback = NoConstructorCallback.class)
    public void shouldNotPopulateConfig_whenCallbackHasNoConstructor() {
        var configBuilder = TestConfig.builder();
        var exception = assertThrows(IllegalStateException.class, () ->  populator.populateConfig(configBuilder, this.getClass()));

        assertEquals(
                BeforeFrameworkInitializationPopulator.NO_CONSTRUCTOR_ERROR.formatted(NoConstructorCallback.class.getName()),
                exception.getMessage()
        );
    }

    @Test
    @BeforeFrameworkInitialization(callback = ExceptionCallback.class)
    public void shouldNotPopulateConfig_whenCallbackThrowsException() {
        var configBuilder = TestConfig.builder();
        var exception = assertThrows(IllegalStateException.class, () ->  populator.populateConfig(configBuilder, this.getClass()));

        assertEquals(
                BeforeFrameworkInitializationPopulator.INSTANTIATION_ERROR.formatted(CAUSE.getMessage()),
                exception.getMessage()
        );
    }

    static class ValidCallback implements Runnable {

        public ValidCallback() {}

        @Override
        public void run() {}
    }

    static class NoConstructorCallback implements Runnable {

        private NoConstructorCallback() {}

        @Override
        public void run() {}
    }

    static class ExceptionCallback implements Runnable {

       public ExceptionCallback() {
           throw CAUSE;
       }

        @Override
        public void run() {}
    }

}
