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
public class PropertiesApplierTest {

    @Mock
    private SystemPropertyHelper systemPropertyHelper;

    @InjectMocks
    private PropertiesTestApplier applier;

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
