/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import io.github.classgraph.ClassGraph;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.RootClassSettings;

@ExtendWith(MockitoExtension.class)
public class RootClassFinderTest {

    @Mock
    private ClassGraph classGraph;

    @InjectMocks
    private RootClassFinder rootClassFinder;

    @Test
    public void shouldReturnTestClass_asRootClass_whenRootClassConfigIsNotProvided() {
        var config = defaultConfigBuilder().build();
        assertEquals(this.getClass(), rootClassFinder.findRootClass(config));
    }

    @Test
    public void shouldUseDirectlySpecifiedRootClass_whenProvided() {
        var config = defaultConfigBuilder()
                .rootClass(String.class)
                .build();
        assertEquals(String.class, rootClassFinder.findRootClass(config));
    }

    @Test
    public void shouldThrowException_whenMultipleConflictingOptionsSelected() {
        var config = defaultConfigBuilder()
                .useTestClassAsRoot(true)
                .findRootClassOnClasspath(true)
                .build();
        assertThrows(IllegalStateException.class, () -> rootClassFinder.findRootClass(config));
    }

    @Test
    public void shouldReturnTestClass_asRootClass_whenUseTestClassOptionIsSelected() {
        var config = defaultConfigBuilder()
                .useTestClassAsRoot(true)
                .build();
        assertEquals(this.getClass(), rootClassFinder.findRootClass(config));
    }

    @Test
    @Disabled("ClassGraph stuff is very difficult to mock")
    public void shouldFindRootClass_onClasspath_whenClasspathOptionSelected_andOneAvailable() {
        fail();
    }

    @Test
    @Disabled("ClassGraph stuff is very difficult to mock")
    public void shouldThrowException_whenFindingRootClassOnClasspath_whenClasspathOptionSelected_andNoneAvailable() {
        fail();
    }

    @Test
    @Disabled("ClassGraph stuff is very difficult to mock")
    public void shouldThrowException_whenFindingRootClassOnClasspath__whenClasspathOptionSelected_andMultipleAvailable() {
        fail();
    }

    private TestConfig.TestConfigBuilder defaultConfigBuilder() {
        return TestConfig.builder()
                .rootClass(RootClassSettings.ROOT_CLASS_NOT_DIRECTLY_SPECIFIED)
                .testClass(this.getClass());
    }


}
