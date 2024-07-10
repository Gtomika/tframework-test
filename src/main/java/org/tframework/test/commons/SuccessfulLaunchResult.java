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

import java.util.Optional;
import lombok.Builder;
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
