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
package org.tframework.test.commons.delayers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.test.commons.SuccessfulLaunchResult;

@ExtendWith(MockitoExtension.class)
public class TestDelayersBundleTest {

    private final SuccessfulLaunchResult launchResult = SuccessfulLaunchResult.builder().build();

    @Mock
    private TestDelayer testDelayer;

    private TestDelayersBundle testDelayersBundle;

    @BeforeEach
    public void setUp() {
        testDelayersBundle = new TestDelayersBundle(10, List.of(testDelayer));
    }

    @Test
    public void shouldNotDelayWhenNoDelayerDecidesToDelay() {
        when(testDelayer.delayTest(launchResult, this.getClass())).thenReturn(false);
        int delayCount = testDelayersBundle.delayTest(launchResult, this.getClass());
        assertEquals(0, delayCount);
    }

    @Test
    public void shouldDelayWhenDelayerDecidesToDelay() {
        when(testDelayer.delayTest(launchResult, this.getClass()))
                .thenReturn(true)
                .thenReturn(true)
                .thenReturn(false);
        int delayCount = testDelayersBundle.delayTest(launchResult, this.getClass());
        assertEquals(2, delayCount);
    }
}
