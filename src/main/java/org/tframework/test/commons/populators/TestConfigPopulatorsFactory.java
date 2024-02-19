/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfigPopulatorsFactory {

    public static List<TestConfigPopulator> createTestConfigPopulators() {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        return List.of(
            new ApplicationNameTestConfigPopulator(annotationScanner),
            new CommandLineArgumentsTestConfigPopulator(annotationScanner),
            new ElementSettingsTestConfigPopulator(annotationScanner),
            new InitializationFailureTestConfigPopulator(annotationScanner),
            new ProfilesTestConfigPopulator(annotationScanner),
            new PropertiesTestConfigPopulator(annotationScanner),
            new RootClassTestConfigPopulator(annotationScanner)
        );
    }

}
