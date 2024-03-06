/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.RootClassSettings;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RootClassPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        annotationScanner.scanOneStrict(testClass, RootClassSettings.class).ifPresent(rootClassAnnotation -> {
            configBuilder.useTestClassAsRoot(rootClassAnnotation.useTestClassAsRoot());
            configBuilder.findRootClassOnClasspath(rootClassAnnotation.findRootClassOnClasspath());
            configBuilder.rootClass(rootClassAnnotation.rootClass());
        });
    }
}
