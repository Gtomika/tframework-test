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
package org.tframework.test.commons.utils;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestActionsUtils {

    public static <T, R> R executeIfExactlyOneIsTrue(T testObject, List<PredicateExecutor<T, R>> predicateExecutors) throws IllegalArgumentException {
        long truePredicates = predicateExecutors.stream()
                .filter(predicateExecutor -> predicateExecutor.predicate().test(testObject))
                .count();

        if(truePredicates != 1) {
            String names = predicateExecutors.stream()
                    .map(PredicateExecutor::name)
                    .collect(Collectors.joining(", "));
            throw new IllegalStateException("Exactly one of these must be true: " + names);
        }

        var executor = predicateExecutors.stream()
                .filter(predicateExecutor -> predicateExecutor.predicate().test(testObject))
                .findFirst()
                .orElseThrow();
        return executor.action().get();
    }

}
