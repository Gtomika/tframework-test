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
package org.tframework.test.commons.delayers;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;
import org.tframework.test.commons.SuccessfulLaunchResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDelayersFactory {

    /**
     * Creates a default {@link TestDelayersBundle} with all the default delayers.
     */
    public static TestDelayersBundle createDefaultDelayers(SuccessfulLaunchResult launchResult, Class<?> testClass) {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        return TestDelayersBundle.of(List.of(
                new FixedTestDelayer(annotationScanner, launchResult, testClass),
                new EventTestDelayer(annotationScanner, launchResult, testClass)
        ));
    }

}
