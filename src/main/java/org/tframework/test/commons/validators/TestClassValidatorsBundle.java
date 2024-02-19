/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.validators;

import java.util.List;

public record TestClassValidatorsBundle(
        List<TestClassValidator> validators
) {

    public void applyAllValidations(Class<?> testClass) throws IllegalStateException {
        validators.forEach(validator -> validator.validateTestClass(testClass));
    }

}
