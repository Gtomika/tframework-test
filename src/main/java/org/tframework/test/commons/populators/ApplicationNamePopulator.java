/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.SetApplicationName;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class ApplicationNamePopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        String applicationName = annotationScanner.scanOneStrict(testClass, SetApplicationName.class)
                .map(SetApplicationName::value)
                .orElse(applicationNameFromConfigOrDefault(configBuilder.build(), testClass));
        configBuilder.applicationName(applicationName);
    }

    private String applicationNameFromConfigOrDefault(TestConfig testConfig, Class<?> testClass) {
        if(testConfig.applicationName() != null) {
            return testConfig.applicationName();
        } else {
            return testClass.getName();
        }
    }
}
