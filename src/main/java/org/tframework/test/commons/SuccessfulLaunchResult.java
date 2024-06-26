/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

import java.util.Optional;

import lombok.Builder;
import org.junit.jupiter.api.BeforeEach;
import org.tframework.core.Application;
import org.tframework.core.elements.context.ElementContext;
import org.tframework.core.elements.dependency.resolver.DependencyResolverAggregator;

@Builder
public record SuccessfulLaunchResult(
        Application application,
        DependencyResolverAggregator dependencyResolver,
        Optional<ElementContext> testClassElementContext
) implements LaunchResult {

    @Override
    public boolean successfulLaunch() {
        return true;
    }
}
