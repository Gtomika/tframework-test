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
