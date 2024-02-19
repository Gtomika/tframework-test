package org.tframework.test.commons.appliers;

import lombok.RequiredArgsConstructor;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

import java.util.List;

@RequiredArgsConstructor
public class TestConfigAppliersBundle implements AutoCloseable {

    private final SystemPropertyHelper systemPropertyHelper;
    private final List<TestConfigApplier> testConfigAppliers;

    public void applyAll(TestConfig testConfig) {
        testConfigAppliers.forEach(applier -> applier.applyTestConfig(testConfig));
    }

    @Override
    public void close() throws Exception {
        systemPropertyHelper.cleanUp();
    }
}
