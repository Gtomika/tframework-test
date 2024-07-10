/*
Copyright 2024 Tamas Gaspar

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
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
