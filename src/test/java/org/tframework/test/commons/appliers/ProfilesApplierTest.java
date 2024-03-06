/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import static org.mockito.AdditionalMatchers.and;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.profiles.scanners.SystemPropertyProfileScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@ExtendWith(MockitoExtension.class)
public class ProfilesApplierTest {

    private static final String EXTENSION_NAME = "test";

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    private ProfilesTestApplier applier;

    @BeforeEach
    void setUp() {
        applier = new ProfilesTestApplier(systemPropertyHelper, EXTENSION_NAME);
    }

    @Test
    public void shouldApplyConfig_whenProfilesPresent() {
        var config = TestConfig.builder()
                .profiles(Set.of("a", "b"))
                .build();
        applier.applyTestConfig(config);

        String expectedPropertyName = SystemPropertyProfileScanner.PROFILES_SYSTEM_PROPERTY + "." + EXTENSION_NAME;
        verify(systemPropertyHelper, times(1)).setIntoSystemProperties(
                eq(expectedPropertyName),
                and(contains("a"), contains("b"))
        );
    }

    @Test
    public void shouldNotApplyConfig_whenNoProfilesPresent() {
        var config = TestConfig.builder().build();
        applier.applyTestConfig(config);

        verifyNoInteractions(systemPropertyHelper);
    }

    @Test
    public void shouldNotApplyConfig_whenProfilesEmpty() {
        var config = TestConfig.builder()
                .profiles(Set.of())
                .build();
        applier.applyTestConfig(config);

        verifyNoInteractions(systemPropertyHelper);
    }
}
