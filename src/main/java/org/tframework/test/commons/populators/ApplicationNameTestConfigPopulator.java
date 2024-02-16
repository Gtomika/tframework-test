/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.extern.slf4j.Slf4j;
import org.tframework.core.reflection.annotations.ComposedAnnotationScanner;
import org.tframework.test.annotations.SetApplicationName;
import org.tframework.test.commons.TestConfig;

@Slf4j
public class ApplicationNameTestConfigPopulator extends TestConfigPopulator {

    ApplicationNameTestConfigPopulator(ComposedAnnotationScanner annotationScanner) {
        super(annotationScanner);
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        String applicationName = annotationScanner.scanOneStrict(testClass, SetApplicationName.class)
                .map(SetApplicationName::value)
                .orElseGet(testClass::getName);
        configBuilder.applicationName(applicationName);
    }
}
