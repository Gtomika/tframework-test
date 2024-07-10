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
package org.tframework.test.commons.validators;

/**
 * The test class validator is responsible for checking the class that contains the
 * currently running tests.
 */
public interface TestClassValidator {

    /**
     * Checks if the test class is valid.
     * @throws IllegalStateException If the test class is not valid.
     */
    void validateTestClass(Class<?> testClass) throws IllegalStateException;

}
