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
package org.tframework.test.commons.appliers;

import java.util.List;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

public record TestConfigAppliersBundle(
        SystemPropertyHelper systemPropertyHelper,
        List<TestConfigApplier> testConfigAppliers
) implements AutoCloseable {

    public void applyAll(TestConfig testConfig) {
        testConfigAppliers.forEach(applier -> applier.applyTestConfig(testConfig));
    }

    @Override
    public void close() throws Exception {
        systemPropertyHelper.cleanUp();
    }
}
