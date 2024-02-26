/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.ElementSettings;

public class ElementSettingsTestConfigPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    ElementSettingsTestConfigPopulator(AnnotationScanner annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        annotationScanner.scanOneStrict(testClass, ElementSettings.class).ifPresent(elementSettingsAnnotation -> {
            configBuilder.rootScanningEnabled(elementSettingsAnnotation.rootScanningEnabled());
            configBuilder.rootHierarchyScanningEnabled(elementSettingsAnnotation.rootHierarchyScanningEnabled());
            configBuilder.internalScanningEnabled(elementSettingsAnnotation.internalScanningEnabled());
        });
    }

}
