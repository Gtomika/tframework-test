/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import org.tframework.core.reflection.annotations.ComposedAnnotationScanner;
import org.tframework.test.annotations.ExpectInitializationFailure;
import org.tframework.test.commons.TestConfig;

public class InitializationFailureTestConfigPopulator extends TestConfigPopulator {

    InitializationFailureTestConfigPopulator(ComposedAnnotationScanner annotationScanner) {
        super(annotationScanner);
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        boolean expectFailure = annotationScanner.hasAnnotation(testClass, ExpectInitializationFailure.class);
        configBuilder.expectInitializationFailure(expectFailure);
    }
}
