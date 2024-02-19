package org.tframework.test.commons.appliers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.test.commons.utils.SystemPropertyHelper;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestConfigAppliersFactory {

    /**
     * Build a {@link TestConfigAppliersBundle} with all necessary {@link TestConfigApplier}s.
     * @param id A unique and nice string for the component that is using these appliers.
     *           For example {@code junit5-extension}.
     */
    public static TestConfigAppliersBundle createTestConfigAppliers(String id) {
        var systemPropertyHelper = new SystemPropertyHelper();
        var appliers = List.of(
                new TestClassElementMarkerTestConfigApplier(systemPropertyHelper, id),
                new ProfilesTestConfigApplier(systemPropertyHelper, id),
                new PropertiesTestConfigApplier(systemPropertyHelper),
                new ElementSettingsConfigApplier(systemPropertyHelper)
        );
        return new TestConfigAppliersBundle(systemPropertyHelper, appliers);
    }

}
