/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import org.tframework.core.reflection.annotations.ComposedAnnotationScanner;
import org.tframework.test.annotations.SetRootClass;
import org.tframework.test.commons.TestConfig;

public class RootClassTestConfigPopulator extends TestConfigPopulator {

    RootClassTestConfigPopulator(ComposedAnnotationScanner annotationScanner) {
        super(annotationScanner);
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        annotationScanner.scanOneStrict(testClass, SetRootClass.class).ifPresent(rootClassAnnotation -> {
            configBuilder.useTestClassAsRoot(rootClassAnnotation.useTestClassAsRoot());
            configBuilder.findRootClassOnClasspath(rootClassAnnotation.findRootClassOnClasspath());
            configBuilder.rootClass(rootClassAnnotation.rootClass());
        });
    }
}
