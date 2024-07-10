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
package org.tframework.test.commons.delayers;

import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;

/**
 * A test delayer decides whether to delay the test execution based on the launch result
 * and the test class. Note that only successful launches are passed to the delayer, failed
 * launches are not delayed.
 */
public abstract class TestDelayer {

    protected final AnnotationScanner annotationScanner;
    protected final SuccessfulLaunchResult launchResult;
    protected final Class<?> testClass;

    protected TestDelayer(
            AnnotationScanner annotationScanner,
            SuccessfulLaunchResult launchResult,
            Class<?> testClass
    ) {
        this.annotationScanner = annotationScanner;
        this.launchResult = launchResult;
        this.testClass = testClass;
        init();
    }

    /**
     * Initializes the delayer. All fields are already set.
     */
    protected abstract void init();

    /**
     * Decides whether to delay the test execution.
     * @param launchResult {@link SuccessfulLaunchResult} describing the launch.
     * @param testClass The test class.
     * @return True if the test should be delayed, false otherwise.
     */
    public abstract boolean delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass);

}
