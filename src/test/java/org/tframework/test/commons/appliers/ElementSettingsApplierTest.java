/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.elements.scanner.RootElementClassScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

@ExtendWith(MockitoExtension.class)
public class ElementSettingsApplierTest {

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    @InjectMocks
    private ElementSettingsApplier applier;

    @Test
    public void shouldApplyConfig() {
        var config = TestConfig.builder()
                .rootScanningEnabled(true)
                .rootHierarchyScanningEnabled(true)
                .build();

        applier.applyTestConfig(config);

        verify(systemPropertyHelper, times(1))
                .setFrameworkPropertyIntoSystemProperties(RootElementClassScanner.ROOT_SCANNING_ENABLED_PROPERTY, "true");
        verify(systemPropertyHelper, times(1))
                .setFrameworkPropertyIntoSystemProperties(RootElementClassScanner.ROOT_HIERARCHY_SCANNING_ENABLED_PROPERTY, "true");
    }
}
