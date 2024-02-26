/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfigPopulatorsFactory {

    public static TestConfigPopulatorsBundle createTestConfigPopulators() {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        var populators = List.of(
            new ApplicationNameTestConfigPopulator(annotationScanner),
            new CommandLineArgumentsTestConfigPopulator(annotationScanner),
            new ElementSettingsTestConfigPopulator(annotationScanner),
            new InitializationFailureTestConfigPopulator(annotationScanner),
            new ProfilesTestConfigPopulator(annotationScanner),
            new PropertiesTestConfigPopulator(annotationScanner),
            new RootClassTestConfigPopulator(annotationScanner)
        );
        return new TestConfigPopulatorsBundle(populators);
    }

}
