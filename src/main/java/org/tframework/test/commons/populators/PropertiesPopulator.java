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
package org.tframework.test.commons.populators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetProperties;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class PropertiesPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        Set<String> properties = new HashSet<>();
        annotationScanner.scan(testClass, SetProperties.class).forEach(propertiesAnnotation -> {
            var propertiesOnAnnotation = Arrays.asList(propertiesAnnotation.value());
            properties.addAll(propertiesOnAnnotation);
        });

        Set<String> existingProperties = configBuilder.build().properties();
        if(existingProperties != null) {
            properties.addAll(existingProperties);
        }
        configBuilder.properties(properties);
    }
}
