package org.tframework.test.commons;

import lombok.extern.slf4j.Slf4j;
import org.tframework.core.Application;
import org.tframework.core.TFramework;
import org.tframework.test.TestApplicationLaunchResult;
import org.tframework.test.commons.utils.RootClassFinder;

@Slf4j
public class TestApplicationLauncher {

    private static final String UNEXPECTED_START_ERROR = "Application initialization was expected to fail, but it succeeded!";

    private final RootClassFinder rootClassFinder;

    public TestApplicationLauncher(RootClassFinder rootClassFinder) {
        this.rootClassFinder = rootClassFinder;
    }

    public TestApplicationLaunchResult launchTestApplication(TestConfig testConfig) throws Exception {
        Application testApplication = null;
        Exception initializationException = null;
        boolean successfulAppInitialization;
        try {
            testApplication = TFramework.start(
                    testConfig.applicationName(),
                    rootClassFinder.findRootClass(testConfig),
                    testConfig.commandLineArguments().toArray(new String[0])
            );

            successfulAppInitialization = true;
        } catch (Exception e) {
            log.warn("TFramework application failed to initialize", e);
            initializationException = e;
            successfulAppInitialization = false;
        }
        checkUnexpectedApplicationState(
                testConfig.expectInitializationFailure(),
                successfulAppInitialization,
                initializationException
        );

        return new TestApplicationLaunchResult(testApplication, successfulAppInitialization, initializationException);
    }

    private void checkUnexpectedApplicationState(
            boolean expectFailure,
            boolean applicationInitialized,
            Exception initializationException
    ) throws Exception {
        if(applicationInitialized && expectFailure) {
            throw new IllegalStateException(UNEXPECTED_START_ERROR);
        }
        if(!applicationInitialized && !expectFailure) {
            //if the app expected to start, but did not, transiently rethrow the original exception
            throw initializationException;
        }
    }

}
