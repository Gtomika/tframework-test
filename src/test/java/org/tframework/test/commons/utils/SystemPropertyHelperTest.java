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
