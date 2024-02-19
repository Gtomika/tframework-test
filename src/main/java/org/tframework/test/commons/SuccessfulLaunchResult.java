/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

import org.tframework.core.Application;
import org.tframework.core.elements.dependency.resolver.DependencyResolverAggregator;

public record SuccessfulLaunchResult(
        Application application,
        DependencyResolverAggregator dependencyResolver
) implements LaunchResult {

    @Override
    public boolean successfulLaunch() {
        return true;
    }
}
