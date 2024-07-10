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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfigAppliersFactory {

    /**
     * Build a {@link TestConfigAppliersBundle} with all necessary {@link TestConfigApplier}s.
     * @param extensionName A unique and nice string for the component that is using these appliers.
     *           For example {@code junit5-extension}.
     */
    public static TestConfigAppliersBundle createTestConfigAppliers(String extensionName) {
        var systemPropertyHelper = new SystemPropertyHelper();
        var appliers = List.of(
                new TestClassElementMarkerTestApplier(systemPropertyHelper, extensionName),
                new ProfilesTestApplier(systemPropertyHelper, extensionName),
                new PropertiesTestApplier(systemPropertyHelper),
                new ElementSettingsApplier(systemPropertyHelper),
                new BeforeFrameworkInitializationApplier()
        );
        return new TestConfigAppliersBundle(systemPropertyHelper, appliers);
    }

}
