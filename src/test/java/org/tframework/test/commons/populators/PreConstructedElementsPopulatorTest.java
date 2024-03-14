/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.tframework.core.elements.PreConstructedElementData;
import org.tframework.test.commons.TestConfig;

public class PreConstructedElementsPopulatorTest {

    private final PreConstructedElementsPopulator populator = new PreConstructedElementsPopulator();

    @Test
    public void shouldInitializePreConstructedElements_whenNotSet() {
        var configBuilder = TestConfig.builder();
        populator.populateConfig(configBuilder, this.getClass());
        assertNotNull(configBuilder.build().preConstructedElements());
    }

    @Test
    public void shouldNotModifyPreConstructedElements_whenSet() {
        var expectedPreConstructedElements = Set.of(PreConstructedElementData.from(new Object()));
        var configBuilder = TestConfig.builder()
                .preConstructedElements(expectedPreConstructedElements);
        populator.populateConfig(configBuilder, this.getClass());
        assertEquals(expectedPreConstructedElements, configBuilder.build().preConstructedElements());
    }

}
