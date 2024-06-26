package org.tframework.test.commons.delayers;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.SuccessfulLaunchResult;
import org.tframework.test.commons.annotations.FixedDelay;

import java.util.Optional;

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
