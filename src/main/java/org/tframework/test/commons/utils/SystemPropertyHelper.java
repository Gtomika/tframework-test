/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.utils;

import static org.tframework.core.properties.parsers.PropertyParsingUtils.LIST_BEGIN_CHARACTER;
import static org.tframework.core.properties.parsers.PropertyParsingUtils.LIST_ELEMENT_SEPARATOR_CHARACTER;
import static org.tframework.core.properties.parsers.PropertyParsingUtils.LIST_END_CHARACTER;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.tframework.core.properties.parsers.PropertyParsingUtils;
import org.tframework.core.properties.parsers.SeparatedProperty;
import org.tframework.core.properties.scanners.SystemPropertyScanner;

/**
 * Utility class for setting system properties and marking them
 * for future cleanup.
 */
public class SystemPropertyHelper {

    private final Set<String> systemPropertiesInUse;

    public SystemPropertyHelper() {
        this.systemPropertiesInUse = new HashSet<>();
    }

    /**
     * Set a general system property.
     */
    public void setIntoSystemProperties(String name, String value) {
        System.setProperty(name, value);
        systemPropertiesInUse.add(name);
    }

    /**
     * Set a raw framework property into the system properties, in a way that it will be
     * picked up the {@link SystemPropertyScanner}.
     */
    public void setRawFrameworkPropertyIntoSystemProperties(String rawProperty) {
        SeparatedProperty separatedProperty = PropertyParsingUtils.separateNameValue(rawProperty);
        setFrameworkPropertyIntoSystemProperties(separatedProperty.name(), separatedProperty.value());
    }

    /**
     * Set a list values framework property into the system properties, in a way that it will be
     * picked up the {@link SystemPropertyScanner}.
     */
    public void setFrameworkPropertyIntoSystemProperties(String name, List<String> values) {
        String value = LIST_BEGIN_CHARACTER +
                String.join(LIST_ELEMENT_SEPARATOR_CHARACTER, values) +
                LIST_END_CHARACTER;
        setFrameworkPropertyIntoSystemProperties(name, value);
    }

    /**
     * Set a single valued framework property into the system properties, in a way that it will be
     * picked up the {@link SystemPropertyScanner}.
     */
    public void setFrameworkPropertyIntoSystemProperties(String name, String value) {
        setIntoSystemProperties(SystemPropertyScanner.PROPERTY_PREFIX + name, value);
    }

    /**
     * Remove any system properties that were set through this helper.
     */
    public void cleanUp() {
        systemPropertiesInUse.forEach(p -> System.getProperties().remove(p));
        systemPropertiesInUse.clear();
    }

    public Set<String> getSystemPropertiesInUse() {
        return Set.copyOf(systemPropertiesInUse);
    }
}
