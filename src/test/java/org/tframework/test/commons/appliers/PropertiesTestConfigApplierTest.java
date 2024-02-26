/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@ExtendWith(MockitoExtension.class)
public class PropertiesTestConfigApplierTest {

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    @InjectMocks
    private PropertiesTestConfigApplier applier;

    @Test
    public void shouldApplyConfig_whenPropertiesPresent() {
        var expectedProperties = Set.of("some.test.prop=a", "other.test.prop=b");
        var config = TestConfig.builder()
                .properties(expectedProperties)
                .build();
        applier.applyTestConfig(config);


        expectedProperties.forEach(expectedProperty -> {
            verify(systemPropertyHelper, times(1))
                    .setRawFrameworkPropertyIntoSystemProperties(expectedProperty);
        });
    }

    @Test
    public void shouldNotApplyConfig_whenPropertiesNotPresent() {
        var config = TestConfig.builder().build();
        applier.applyTestConfig(config);

        verifyNoInteractions(systemPropertyHelper);
    }

}
