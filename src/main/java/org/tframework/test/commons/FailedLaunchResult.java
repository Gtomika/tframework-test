/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

public record FailedLaunchResult(
        Exception initializationException
) implements LaunchResult {

    @Override
    public boolean successfulLaunch() {
        return false;
    }
}
