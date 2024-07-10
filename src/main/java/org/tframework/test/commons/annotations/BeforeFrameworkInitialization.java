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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with TFramework tests, to specify {@link Runnable}s to
 * execute before the framework initialized. This annotation may be useful because {@link org.junit.jupiter.api.BeforeAll}
 * methods are invoked <b>after</b> the framework is initialized, and thus, cannot affect the initialization
 * process.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeFrameworkInitialization {

    /**
     * Specifies the {@link Runnable} class to use. This class must have a public, no args constructor.
     */
    Class<? extends Runnable> callback();

}
