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
                new ApplicationNamePopulator(annotationScanner),
                new CommandLineArgumentsPopulator(annotationScanner),
                new ElementSettingsPopulator(annotationScanner),
                new InitializationFailurePopulator(annotationScanner),
                new ProfilesPopulator(annotationScanner),
                new PropertiesPopulator(annotationScanner),
                new RootClassPopulator(annotationScanner),
                new BeforeFrameworkInitializationPopulator(annotationScanner),
                new PreConstructedElementsPopulator()
        );
        return new TestConfigPopulatorsBundle(populators);
    }

}
