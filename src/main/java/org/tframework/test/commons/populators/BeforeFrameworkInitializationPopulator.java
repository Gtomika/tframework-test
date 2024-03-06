/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.BeforeFrameworkInitialization;

import java.lang.reflect.InvocationTargetException;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class BeforeFrameworkInitializationPopulator implements TestConfigPopulator {

    static final String NO_CONSTRUCTOR_ERROR = "Specified callback '%s' must have a public, no-args constructor";
    static final String INSTANTIATION_ERROR = "Specified callback could not be instantiated: %s";

    private final AnnotationScanner annotationScanner;

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        var callbacks = annotationScanner.scan(testClass, BeforeFrameworkInitialization.class).stream()
                .map(BeforeFrameworkInitialization::callback)
                .map(callbackClass -> {
                    try {
                        return callbackClass.getConstructor().newInstance();
                    } catch (InvocationTargetException e) {
                        throw new IllegalStateException(INSTANTIATION_ERROR.formatted(e.getTargetException().getMessage()));
                    } catch (Exception e) {
                        throw new IllegalStateException(NO_CONSTRUCTOR_ERROR.formatted(callbackClass.getName()));
                    }
                })
                .toList();
        configBuilder.beforeFrameworkCallbacks(callbacks);
    }
}
