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
package org.tframework.test.commons.populators;

import java.lang.reflect.InvocationTargetException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.BeforeFrameworkInitialization;

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
