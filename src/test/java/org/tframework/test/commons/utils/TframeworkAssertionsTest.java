/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.core.elements.ElementNotFoundException;
import org.tframework.core.elements.ElementsContainer;
import org.tframework.core.elements.context.ElementContext;
import org.tframework.core.initializers.InitializationException;
import org.tframework.core.profiles.ProfilesContainer;
import org.tframework.core.profiles.scanners.DefaultProfileScanner;
import org.tframework.core.properties.ListPropertyValue;
import org.tframework.core.properties.PropertiesContainer;
import org.tframework.core.properties.Property;
import org.tframework.core.properties.SinglePropertyValue;

@ExtendWith(MockitoExtension.class)
public class TframeworkAssertionsTest {

    @Mock
    private ElementContext elementContext;

    @Test
    public void shouldAssertHasProfiles() {
        var expectedProfiles = Set.of("a", "b");
        var container = ProfilesContainer.fromProfiles(expectedProfiles);
        TframeworkAssertions.assertHasProfiles(container, expectedProfiles);
    }

    @Test
    public void shouldAssertHasProfile() {
        var expectedProfiles = Set.of("a", "b");
        var container = ProfilesContainer.fromProfiles(expectedProfiles);
        TframeworkAssertions.assertHasProfile(container, "a");
    }

    @Test
    public void shouldAssertHasNoProfile() {
        var expectedProfiles = Set.of("a", "b");
        var container = ProfilesContainer.fromProfiles(expectedProfiles);
        TframeworkAssertions.assertHasNoProfile(container, "c");
    }

    @Test
    public void shouldAssertHasNonDefaultProfiles() {
        var expectedProfiles = Set.of("a", "b");
        var container = ProfilesContainer.fromProfiles(Set.of("a", "b", DefaultProfileScanner.DEFAULT_PROFILE_NAME));
        TframeworkAssertions.assertHasNonDefaultProfiles(container, expectedProfiles);
    }

    @Test
    public void shouldAssertHasPropertyWithValueString() {
        var expectedValue = new SinglePropertyValue("test");
        var expectedProperty = new Property("cool.prop", expectedValue);
        var container = PropertiesContainer.fromProperties(List.of(expectedProperty));

        TframeworkAssertions.assertHasPropertyWithValue(container, "cool.prop", expectedValue.value());
        TframeworkAssertions.assertHasPropertyWithValue(container, "cool.prop", expectedValue);
        TframeworkAssertions.assertHasProperty(container, expectedProperty);
    }

    @Test
    public void shouldAssertHasPropertyWithValueStringList() {
        var expectedValue = new ListPropertyValue(List.of("t1", "t2"));
        var expectedProperty =  new Property("cool.prop", expectedValue);
        var container = PropertiesContainer.fromProperties(List.of(expectedProperty));

        TframeworkAssertions.assertHasPropertyWithValue(container, "cool.prop", expectedValue.values());
        TframeworkAssertions.assertHasPropertyWithValue(container, "cool.prop", expectedValue);
        TframeworkAssertions.assertHasProperty(container, expectedProperty);
    }

    @Test
    public void shouldAssertHasNoProperty() {
        var expectedProperty =  new Property("cool.prop", new SinglePropertyValue("test"));
        var container = PropertiesContainer.fromProperties(List.of(expectedProperty));

        TframeworkAssertions.assertHasNoProperty(container,"not.cool.prop");
    }

    @Test
    public void shouldAssertHasElementByName() {
        when(elementContext.getName()).thenReturn("e");
        when(elementContext.requestInstance()).thenReturn("test");
        var container = ElementsContainer.fromElementContexts(List.of(elementContext));

        var elementInstance = TframeworkAssertions.assertHasElement(container,"e");
        assertEquals("test", elementInstance);
    }

    @Test
    public void shouldAssertHasElementByType() {
        doReturn(String.class).when(elementContext).getType();
        when(elementContext.requestInstance()).thenReturn("test");
        var container = ElementsContainer.fromElementContexts(List.of(elementContext));

        var elementInstance = TframeworkAssertions.assertHasElement(container, String.class);
        assertEquals("test", elementInstance);
    }

    @Test
    public void shouldAssertHasNoElementByName() {
        when(elementContext.getName()).thenReturn("e");
        var container = ElementsContainer.fromElementContexts(List.of(elementContext));

        TframeworkAssertions.assertHasNoElement(container, "x");
    }

    @Test
    public void shouldAssertHasNoElementByType() {
        doReturn(String.class).when(elementContext).getType();
        var container = ElementsContainer.fromElementContexts(List.of(elementContext));

        TframeworkAssertions.assertHasNoElement(container, File.class);
    }

    @Test
    public void shouldAssertInitializationExceptionWithRootCause() {
        var expectedRootCause = new ElementNotFoundException("test");
        var initializationException = new InitializationException(expectedRootCause);

        var actualRootCause = TframeworkAssertions.assertInitializationExceptionWithCause(
                initializationException, ElementNotFoundException.class
        );
        assertEquals(expectedRootCause, actualRootCause);
    }

}
