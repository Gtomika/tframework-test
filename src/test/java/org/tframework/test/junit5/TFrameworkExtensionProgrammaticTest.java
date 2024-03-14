/* Licensed under Apache-2.0 2024. */
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.tframework.test.commons.utils.TframeworkAssertions.assertHasProfile;

import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.tframework.core.Application;
import org.tframework.core.elements.PreConstructedElementData;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.profiles.ProfilesContainer;

@Element
public class TFrameworkExtensionProgrammaticTest {

    private static final Object MY_PRE_CONSTRUCTED_ELEMENT = new Object();

    @RegisterExtension
    public static TFrameworkExtension tFrameworkExtension = TFrameworkExtension.tframeworkTest()
            .profiles(Set.of("test"))
            .properties(Set.of("cool.prop=123"))
            .applicationName("myCoolTestApp")
            .rootClass(DummyRootClass.class)
            .preConstructedElements(Set.of(
                    PreConstructedElementData.builder()
                            .name("myPreConstructedElement")
                            .preConstructedInstance(MY_PRE_CONSTRUCTED_ELEMENT)
                            .build()
            ))
            .build()
            .toJunit5Extension();

    @Test
    public void shouldRun(
            @InjectElement Application application,
            @InjectElement ProfilesContainer profilesContainer,
            @InjectElement("myPreConstructedElement") Object myPreConstructedElement,
            @InjectProperty("cool.prop") int coolProp
    ) {
        assertEquals("myCoolTestApp", application.getName());
        assertEquals(DummyRootClass.class, application.getRootClass());

        assertHasProfile(profilesContainer, "test");
        assertEquals(123, coolProp);

        assertSame(MY_PRE_CONSTRUCTED_ELEMENT, myPreConstructedElement);
    }

}
