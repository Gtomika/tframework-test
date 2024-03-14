/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import java.util.Set;
import org.tframework.test.commons.TestConfig;

/*
Adding pre-constructed elements from annotations is not supported: annotations cannot
accept arbitrary objects. However, they can still be set when the config is created
programmatically.
 */
public class PreConstructedElementsPopulator implements TestConfigPopulator {

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        if(configBuilder.build().preConstructedElements() == null) {
            configBuilder.preConstructedElements(Set.of());
        }
    }
}
