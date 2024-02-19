/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;

/**
 * For all components that scan and analyze annotations to populate the {@link TestConfig}.
 */
public interface TestConfigPopulator {

    /**
     * Inspects the test class and set the results into the {@link TestConfig} builder.
     */
   void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass);

}
