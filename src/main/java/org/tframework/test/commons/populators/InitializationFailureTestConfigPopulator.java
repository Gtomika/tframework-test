/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ExpectInitializationFailure;

public class InitializationFailureTestConfigPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    InitializationFailureTestConfigPopulator(AnnotationScanner annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        boolean expectFailure = annotationScanner.hasAnnotation(testClass, ExpectInitializationFailure.class);

        if(!configBuilder.build().expectInitializationFailure()) {
            configBuilder.expectInitializationFailure(expectFailure);
        }
    }
}
