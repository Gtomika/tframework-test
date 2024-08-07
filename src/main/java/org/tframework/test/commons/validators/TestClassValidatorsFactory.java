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
package org.tframework.test.commons.validators;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestClassValidatorsFactory {

    public static TestClassValidatorsBundle createTestClassValidators() {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        List<TestClassValidator> validators = List.of(
                new IsElementTestClassValidator(annotationScanner)
        );
        return new TestClassValidatorsBundle(validators);
    }

}
