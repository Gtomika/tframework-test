/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.delayers;

import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScannersFactory;
import org.tframework.test.commons.SuccessfulLaunchResult;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDelayersFactory {

    /**
     * Creates a default {@link TestDelayersBundle} with all the default delayers.
     */
    public static TestDelayersBundle createDefaultDelayers(SuccessfulLaunchResult launchResult, Class<?> testClass) {
        var annotationScanner = AnnotationScannersFactory.createComposedAnnotationScanner();
        return TestDelayersBundle.of(List.of(
                new FixedTestDelayer(annotationScanner, launchResult, testClass),
                new EventTestDelayer(annotationScanner, launchResult, testClass)
        ));
    }

}
