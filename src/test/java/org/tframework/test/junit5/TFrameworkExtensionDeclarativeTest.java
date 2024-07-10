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
package org.tframework.test.junit5;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.tframework.test.commons.utils.TframeworkAssertions.assertHasProfile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.tframework.core.Application;
import org.tframework.core.elements.annotations.Element;
import org.tframework.core.elements.annotations.InjectElement;
import org.tframework.core.elements.annotations.InjectProperty;
import org.tframework.core.profiles.ProfilesContainer;
import org.tframework.test.commons.annotations.ElementSettings;
import org.tframework.test.commons.annotations.RootClassSettings;
import org.tframework.test.commons.annotations.SetApplicationName;
import org.tframework.test.commons.annotations.SetProfiles;
import org.tframework.test.commons.annotations.SetProperties;

@SetProfiles("test")
@SetProperties("cool.prop=123")
@SetApplicationName("myCoolTestApp")
@RootClassSettings(rootClass = DummyRootClass.class)
@ElementSettings(
        rootScanningEnabled = true,
        rootHierarchyScanningEnabled = false
)
@Element
@ExtendWith(TFrameworkExtension.class)
public class TFrameworkExtensionDeclarativeTest {

    @Test
    public void shouldRun(
            @InjectElement Application application,
            @InjectElement ProfilesContainer profilesContainer,
            @InjectProperty("cool.prop") int coolProp
    ) {
        assertEquals("myCoolTestApp", application.getName());
        assertEquals(DummyRootClass.class, application.getRootClass());

        assertHasProfile(profilesContainer, "test");
        assertEquals(123, coolProp);
    }

}
