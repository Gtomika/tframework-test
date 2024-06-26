/* Licensed under Apache-2.0 2024. */
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
