/* Licensed under Apache-2.0 2024. */
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
