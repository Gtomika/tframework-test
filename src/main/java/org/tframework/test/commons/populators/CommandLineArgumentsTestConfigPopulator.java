/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.annotations.SetCommandLineArguments;
import org.tframework.test.commons.TestConfig;

@Slf4j
public class CommandLineArgumentsTestConfigPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    CommandLineArgumentsTestConfigPopulator(AnnotationScanner annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        List<String> commandLineArguments = annotationScanner.scanOneStrict(testClass, SetCommandLineArguments.class)
                .map(a -> new ArrayList<>(Arrays.asList(a.value())))
                .orElseGet(ArrayList::new);

        List<String> existingArguments = configBuilder.build().commandLineArguments();
        if(existingArguments != null) {
            commandLineArguments.addAll(existingArguments);
        }
        configBuilder.commandLineArguments(commandLineArguments);
    }
}
