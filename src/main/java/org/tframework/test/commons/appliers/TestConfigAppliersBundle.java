/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.appliers;

import java.util.List;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.utils.SystemPropertyHelper;

public record TestConfigAppliersBundle(
        SystemPropertyHelper systemPropertyHelper,
        List<TestConfigApplier> testConfigAppliers
) implements AutoCloseable {

    public void applyAll(TestConfig testConfig) {
        testConfigAppliers.forEach(applier -> applier.applyTestConfig(testConfig));
    }

    @Override
    public void close() throws Exception {
        systemPropertyHelper.cleanUp();
    }
}
