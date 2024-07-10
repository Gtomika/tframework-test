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
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to specify element related settings.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface ElementSettings {

    /**
     * Control if the root scanning should be enabled.
     * @see org.tframework.core.elements.scanner.RootElementClassScanner
     */
    boolean rootScanningEnabled() default true;

    /**
     * Control if the root hierarchy scanning should be enabled.
     * @see org.tframework.core.elements.scanner.RootElementClassScanner
     */
    boolean rootHierarchyScanningEnabled() default true;
}
