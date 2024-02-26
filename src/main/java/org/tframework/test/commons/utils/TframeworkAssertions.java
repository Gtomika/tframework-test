/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.opentest4j.AssertionFailedError;
import org.tframework.core.elements.ElementNotFoundException;
import org.tframework.core.elements.ElementsContainer;
import org.tframework.core.initializers.InitializationException;
import org.tframework.core.profiles.ProfilesContainer;
import org.tframework.core.profiles.scanners.DefaultProfileScanner;
import org.tframework.core.properties.ListPropertyValue;
import org.tframework.core.properties.PropertiesContainer;
import org.tframework.core.properties.Property;
import org.tframework.core.properties.PropertyValue;
import org.tframework.core.properties.SinglePropertyValue;

/**
 * Utilities for making assertions on TFramework applications.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TframeworkAssertions {

    /**
     * Assert that the {@link ProfilesContainer} has the given profiles.
     */
    public static void assertHasProfiles(ProfilesContainer profilesContainer, Set<String> expectedProfiles) {
        assertEquals(expectedProfiles, profilesContainer.profiles());
    }

    /**
     * Asserts that the given profile is set.
     */
    public static void assertHasProfile(ProfilesContainer profilesContainer, String profile) {
        assertTrue(profilesContainer.isProfileSet(profile));
    }

    /**
     * Asserts that the given profile is <b>not</b> set.
     */
    public static void assertHasNoProfile(ProfilesContainer profilesContainer, String profile) {
        assertFalse(profilesContainer.isProfileSet(profile));
    }

    /**
     * Assert that the {@link ProfilesContainer} has the provided profiles, plus the
     * {@link DefaultProfileScanner#DEFAULT_PROFILE_NAME}.
     */
    public static void assertHasNonDefaultProfiles(ProfilesContainer profilesContainer, Set<String> expectedNonDefaultProfiles) {
        var expectedProfiles = new HashSet<>(expectedNonDefaultProfiles);
        expectedProfiles.add(DefaultProfileScanner.DEFAULT_PROFILE_NAME);

        assertEquals(expectedProfiles, profilesContainer.profiles());
    }

    /**
     * Asserts that the given {@link PropertiesContainer} contains the property with
     * the specified name and value. This property is expected to be {@link SinglePropertyValue}.
     */
    public static void assertHasPropertyWithValue(
            PropertiesContainer propertiesContainer,
            String propertyName,
            String propertyValue
    ) {
        if(propertiesContainer.getPropertyValueObject(propertyName) instanceof SinglePropertyValue spv) {
            assertEquals(propertyValue, spv.value());
        } else {
            fail("Property with name " + propertyName + " is not a single valued property");
        }
    }

    /**
     * Asserts that the given {@link PropertiesContainer} contains the property with
     * the specified name and values. This property is expected to be {@link ListPropertyValue}.
     */
    public static void assertHasPropertyWithValue(
            PropertiesContainer propertiesContainer,
            String propertyName,
            List<String> propertyValues
    ) {
        if(propertiesContainer.getPropertyValueObject(propertyName) instanceof ListPropertyValue lpv) {
            assertEquals(propertyValues, lpv.values());
        } else {
            fail("Property with name " + propertyName + " is not a list valued property");
        }

    }

    /**
     * Asserts that the given {@link PropertiesContainer} contains the property with
     * the specified name and {@link PropertyValue}.
     */
    public static void assertHasPropertyWithValue(
            PropertiesContainer propertiesContainer,
            String propertyName,
            PropertyValue propertyValue
    ) {
        assertEquals(propertyValue, propertiesContainer.getPropertyValueObject(propertyName));
    }

    /**
     * Asserts that the given {@link PropertiesContainer} contains the {@link Property}.
     */
    public static void assertHasProperty(
            PropertiesContainer propertiesContainer,
            Property property
    ) {
        assertEquals(property.value(), propertiesContainer.getPropertyValueObject(property.name()));
    }

    /**
     * Asserts that the given property is not set.
     */
    public static void assertHasNoProperty(PropertiesContainer propertiesContainer, String propertyName) {
        assertFalse(propertiesContainer.propertyNames().contains(propertyName));
    }

    /**
     * Asserts that the {@link ElementsContainer} contains an element with the given name.
     * @return The element with the name, if present.
     */
    public static Object assertHasElement(ElementsContainer elementsContainer, String elementName) {
        try {
            var context = elementsContainer.getElementContext(elementName);
            return context.requestInstance();
        } catch (ElementNotFoundException e) {
            fail("Element with name " + elementName + " is not present");
            return null;
        }
    }

    /**
     * Asserts that the {@link ElementsContainer} contains an element with the given type.
     * @return The element with the type, if present.
     * @see ElementsContainer#hasElementContext(Class)
     */
    @SuppressWarnings("unchecked")
    public static <T> T assertHasElement(ElementsContainer elementsContainer, Class<T> elementType) {
        try {
            var context = elementsContainer.getElementContext(elementType);
            return ((T) context.requestInstance());
        } catch (ElementNotFoundException e) {
            fail("Element with type " + elementType.getName() + " is not present");
            return null;
        }
    }

    /**
     * Asserts that the {@link ElementsContainer} does not have an element with the given name.
     */
    public static void assertHasNoElement(ElementsContainer elementsContainer, String elementName) {
        assertFalse(elementsContainer.hasElementContext(elementName));
    }

    /**
     * Asserts that the {@link ElementsContainer} does not have an element with the given type.
     */
    public static void assertHasNoElement(ElementsContainer elementsContainer, Class<?> elementType) {
        assertFalse(elementsContainer.hasElementContext(elementType));
    }

    /**
     * Asserts that the exception is an {@link InitializationException} with the given type of ROOT cause.
     * @return If the assertion was successful, returns the root cause exception for additional checks.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Exception> E assertInitializationExceptionWithCause(Exception exception, Class<E> expectedCauseType) {
        if(exception instanceof InitializationException) {
            Throwable actualCause = Stream.iterate(exception, Throwable::getCause)
                    .filter(element -> element.getCause() == null)
                    .findFirst()
                    .orElseThrow(() -> new AssertionFailedError("Initialization exception has no cause"));
            assertEquals(expectedCauseType, actualCause.getClass());
            return (E) actualCause;
        } else {
            fail("Exception was not " + InitializationException.class.getName() + ", but " + exception.getClass().getName());
            return null;
        }
    }

}
