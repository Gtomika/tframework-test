/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import java.util.List;
import org.tframework.test.commons.TestConfig;

public record TestConfigPopulatorsBundle(
        List<TestConfigPopulator> populators
) {

    public void populateWithAll(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        configBuilder.testClass(testClass);
        populators.forEach(populator -> populator.populateConfig(configBuilder, testClass));
    }

}
