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
package org.tframework.test.commons;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.Application;
import org.tframework.core.initializers.InitializationException;
import org.tframework.test.commons.utils.RootClassFinder;

@ExtendWith(MockitoExtension.class)
public class TestApplicationLauncherTest {

    @Mock
    private RootClassFinder rootClassFinder;

    @Mock
    private TestApplicationLauncher.AppLauncher appLauncher;

    @InjectMocks
    private TestApplicationLauncher testApplicationLauncher;

    @BeforeEach
    public void setUp() {
        doReturn(this.getClass()).when(rootClassFinder).findRootClass(any(TestConfig.class));
    }

    @Test
    public void shouldReturnSuccessfulLaunchResult_whenApplicationStarts() throws Exception {
        var config = TestConfig.builder()
                .testClass(this.getClass())
                .expectInitializationFailure(false)
                .build();
        var expectedApplication = Application.empty();

        when(appLauncher.startApp(config, this.getClass())).thenReturn(expectedApplication);

        var launchResult = testApplicationLauncher.launchTestApplication(config);

        if(launchResult instanceof SuccessfulLaunchResult successfulLaunchResult) {
            assertEquals(expectedApplication, successfulLaunchResult.application());
        } else {
            fail("Launch result should be successful");
        }
    }

    @Test
    public void shouldReturnFailedLaunchResult_whenApplicationThrowsException() throws Exception {
        var config = TestConfig.builder()
                .testClass(this.getClass())
                .expectInitializationFailure(true)
                .build();
        var expectedException = new InitializationException("oof");

        when(appLauncher.startApp(config, this.getClass())).thenThrow(expectedException);

        var launchResult = testApplicationLauncher.launchTestApplication(config);

        if(launchResult instanceof FailedLaunchResult failedLaunchResult) {
            assertEquals(expectedException, failedLaunchResult.initializationException());
        } else {
            fail("Launch result should be failed");
        }
    }

    @Test
    public void shouldRethrowInitializationException_whenApplicationThrowsException_andItIsUnexpected() {
        var config = TestConfig.builder()
                .testClass(this.getClass())
                .expectInitializationFailure(false)
                .build();
        var expectedException = new InitializationException("oof");

        when(appLauncher.startApp(config, this.getClass())).thenThrow(expectedException);

        var actualException = assertThrows(InitializationException.class, () -> testApplicationLauncher.launchTestApplication(config));
        assertEquals(expectedException, actualException);
    }

    @Test
    public void shouldThrowException_whenApplicationStarts_butItWasExpectedToFail() {
        var config = TestConfig.builder()
                .testClass(this.getClass())
                .expectInitializationFailure(true)
                .build();
        when(appLauncher.startApp(config, this.getClass())).thenReturn(Application.empty());

        var exception = assertThrows(IllegalStateException.class, () -> testApplicationLauncher.launchTestApplication(config));
        assertEquals(TestApplicationLauncher.UNEXPECTED_START_ERROR, exception.getMessage());
    }
}
