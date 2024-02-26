/* Licensed under Apache-2.0 2024. */
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
                new TestClassElementMarkerTestConfigApplier(systemPropertyHelper, extensionName),
                new ProfilesTestConfigApplier(systemPropertyHelper, extensionName),
                new PropertiesTestConfigApplier(systemPropertyHelper),
                new ElementSettingsConfigApplier(systemPropertyHelper)
        );
        return new TestConfigAppliersBundle(systemPropertyHelper, appliers);
    }

}
