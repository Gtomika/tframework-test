/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

public sealed interface LaunchResult permits SuccessfulLaunchResult, FailedLaunchResult {

    boolean successfulLaunch();

}
