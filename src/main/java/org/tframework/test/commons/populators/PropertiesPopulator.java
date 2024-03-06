/* Licensed under Apache-2.0 2024. */
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
