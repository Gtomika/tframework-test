/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.tframework.core.properties.parsers.PropertyParsingUtils;
import org.tframework.core.properties.scanners.SystemPropertyScanner;

public class SystemPropertyHelperTest {

    private final SystemPropertyHelper systemPropertyHelper = new SystemPropertyHelper();

    @AfterEach
    public void tearDown() {
        systemPropertyHelper.cleanUp();
    }

    @Test
    public void shouldSetIntoSystemProperties() {
        systemPropertyHelper.setIntoSystemProperties("prop", "val");
        assertTrue(systemPropertyHelper.getSystemPropertiesInUse().contains("prop"));
        assertEquals("val", System.getProperty("prop"));
    }

    @Test
    public void shouldSetRawFrameworkProperty_intoSystemProperties() {
        String rawFrameworkProp = "prop" + PropertyParsingUtils.PROPERTY_NAME_VALUE_SEPARATOR + "val";
        systemPropertyHelper.setRawFrameworkPropertyIntoSystemProperties(rawFrameworkProp);

        String expectedSystemPropertyName = SystemPropertyScanner.PROPERTY_PREFIX + "prop";
        assertTrue(systemPropertyHelper.getSystemPropertiesInUse().contains(expectedSystemPropertyName));
        assertEquals("val", System.getProperty(expectedSystemPropertyName));
    }

    @Test
    public void shouldSetFrameworkProperty_intoSystemProperties() {
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties("prop", "val");

        String expectedSystemPropertyName = SystemPropertyScanner.PROPERTY_PREFIX + "prop";
        assertTrue(systemPropertyHelper.getSystemPropertiesInUse().contains(expectedSystemPropertyName));
        assertEquals("val", System.getProperty(expectedSystemPropertyName));
    }

    @Test
    public void shouldSetFrameworkListProperty_intoSystemProperties() {
        systemPropertyHelper.setFrameworkPropertyIntoSystemProperties("prop", List.of("val1", "val2"));

        String expectedSystemPropertyName = SystemPropertyScanner.PROPERTY_PREFIX + "prop";
        String expectedSystemPropertyValue = PropertyParsingUtils.LIST_BEGIN_CHARACTER +
                "val1" + PropertyParsingUtils.LIST_ELEMENT_SEPARATOR_CHARACTER + "val2" +
                PropertyParsingUtils.LIST_END_CHARACTER;

        assertTrue(systemPropertyHelper.getSystemPropertiesInUse().contains(expectedSystemPropertyName));
        assertEquals(expectedSystemPropertyValue, System.getProperty(expectedSystemPropertyName));
    }
}
