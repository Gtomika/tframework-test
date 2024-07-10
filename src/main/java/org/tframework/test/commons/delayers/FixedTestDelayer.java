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

import java.util.Optional;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.annotations.FixedDelay;

/**
 * A {@link TestDelayer} that scans for {@link FixedDelay} annotation on test classes and
 * delays the test execution accordingly.
 */
@Slf4j
public class FixedTestDelayer extends TestDelayer {

    @Getter
    private long creationTime;
    private Optional<FixedDelay> fixedDelayAnnotation;

    FixedTestDelayer(
            AnnotationScanner annotationScanner,
            SuccessfulLaunchResult launchResult,
            Class<?> testClass
    ) {
        super(annotationScanner, launchResult, testClass);
    }

    @Override
    protected void init() {
        creationTime = System.currentTimeMillis();
        fixedDelayAnnotation = annotationScanner.scanOneStrict(testClass, FixedDelay.class);
    }

    @Override
    public boolean delayTest(SuccessfulLaunchResult launchResult, Class<?> testClass) {
        if(fixedDelayAnnotation.isPresent()) {
            long timePassed = System.currentTimeMillis() - creationTime;
            if(timePassed >= fixedDelayAnnotation.get().millis()) {
                log.debug("Fixed delay of {} ms has passed for test class {}, not delaying.",
                        fixedDelayAnnotation.get().millis(), testClass.getName());
                return false;
            } else {
                log.info("Fixed delay of {} ms has not passed for test class {}, delaying.",
                        fixedDelayAnnotation.get().millis(), testClass.getName());
                return true;
            }
        } else {
            log.debug("No @FixedDelay annotation found on test class {}, not delaying.", testClass.getName());
            return false;
        }
    }
}
