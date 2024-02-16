/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.ComposedAnnotationScanner;
import org.tframework.test.commons.TestConfig;

/**
 * For all components that scan and analyze annotations to populate the {@link TestConfig}.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class TestConfigPopulator {

    protected final ComposedAnnotationScanner annotationScanner;

    /**
     * Inspects the test class and set the results into the {@link TestConfig} builder.
     */
    public abstract void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass);

}
