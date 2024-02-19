package org.tframework.test;

import org.tframework.core.Application;

public record TestApplicationLaunchResult(
        Application testApplication,
        boolean successfulLaunch,
        Exception initializationException
) {
}
