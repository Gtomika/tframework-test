package org.tframework.test.commons.appliers;

import org.tframework.test.commons.TestConfig;

/**
 * Performs actions based on the {@link org.tframework.test.commons.TestConfig},
 * before the test application is initialized.
 */
public interface TestConfigApplier {

    /**
     * Performs the actions before the test application is initialized.
     */
    void applyTestConfig(TestConfig testConfig);

}
