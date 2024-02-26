/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import org.tframework.test.commons.TestConfig;

/**
 * For all components that scan and analyze annotations to populate the {@link TestConfig}.
 */
public interface TestConfigPopulator {

    /**
     * Inspects the test class and sets the results into the {@link TestConfig} builder.
     * Implementations should be mindful of the already existing values in the config.
     */
   void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass);

}
