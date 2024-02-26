/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.validators;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestClassValidatorsFactory {

    public static TestClassValidatorsBundle createTestClassValidators() {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        List<TestClassValidator> validators = List.of(
                new IsElementTestClassValidator(annotationScanner)
        );
        return new TestClassValidatorsBundle(validators);
    }

}
