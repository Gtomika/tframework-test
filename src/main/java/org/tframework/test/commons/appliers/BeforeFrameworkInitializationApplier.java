package org.tframework.test.commons.appliers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.test.commons.TestConfig;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class BeforeFrameworkInitializationApplier implements TestConfigApplier {

    @Override
    public void applyTestConfig(TestConfig testConfig) {
        testConfig.beforeFrameworkCallbacks().forEach(Runnable::run);
    }
}
