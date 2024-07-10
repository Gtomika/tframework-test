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
package org.tframework.test.commons.utils;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.tframework.core.TFrameworkRootClass;
import org.tframework.test.commons.TestConfig;
import org.tframework.test.commons.annotations.RootClassSettings;

/**
 * This class is responsible for finding the root class of the test application,
 * based on the configuration specified in {@link TestConfig}.
 */
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class RootClassFinder {

    private static final int SCAN_THREAD_AMOUNT = 5;

    private static final String TOO_MANY_ROOT_CLASSES_ERROR_TEMPLATE = "More than one class was found to annotated with '" +
            TFrameworkRootClass.class.getName() + "' on the classpath: %s";
    private static final String NO_ROOT_CLASS_ERROR = "No root class was found that is annotated with '" +
            TFrameworkRootClass.class.getName() + "', but exactly one is required.";

    private final ClassGraph classGraph;

    public Class<?> findRootClass(TestConfig testConfig) {
        if(noRootClassConfigProvided(testConfig)) {
            return testConfig.testClass();
        }
        if(rootClassDirectlySpecified(testConfig)) {
            return testConfig.rootClass();
        } else {
            //root class was not specified, let's check the boolean fields
            //this will run only of exactly one field is true, and the corresponding action will be used to find root class
            return TestActionsUtils.executeIfExactlyOneIsTrue(testConfig, List.of(
                    new PredicateExecutor<>(
                            "useTestClassAsRoot",
                            TestConfig::useTestClassAsRoot,
                            testConfig::testClass
                    ),
                    new PredicateExecutor<>(
                            "findRootClassOnClasspath",
                            TestConfig::findRootClassOnClasspath,
                            this::findRootClassOnClasspath
                    )
            ));
        }
    }

    private boolean rootClassDirectlySpecified(TestConfig testConfig) {
        //root class field is set
        return testConfig.rootClass() != null &&
                //and it is set to something other than the default value
                !Objects.equals(testConfig.rootClass(), RootClassSettings.ROOT_CLASS_NOT_DIRECTLY_SPECIFIED);
    }

    private Class<?> findRootClassOnClasspath() {
        try(var scanResult = classGraph.scan(Executors.newFixedThreadPool(SCAN_THREAD_AMOUNT), SCAN_THREAD_AMOUNT)) {
            var rootClassCandidates = scanResult.getAllClasses()
                    .filter(this::isClassDirectlyAnnotatedWithTframeworkRoot);

            if(rootClassCandidates.size() > 1) {
                throw new IllegalStateException(TOO_MANY_ROOT_CLASSES_ERROR_TEMPLATE.formatted(rootClassCandidates.getNames()));
            }
            if(rootClassCandidates.isEmpty()) {
                throw new IllegalStateException(NO_ROOT_CLASS_ERROR);
            }
            return rootClassCandidates.getFirst().loadClass();
        }
    }

    private boolean isClassDirectlyAnnotatedWithTframeworkRoot(ClassInfo info) {
        boolean isDirectlyAnnotated = info.getAnnotationInfo()
                .directOnly()
                .stream()
                .anyMatch(annotationInfo -> {
            return annotationInfo.getName().equals(TFrameworkRootClass.class.getName());
        });
        return isDirectlyAnnotated && info.isStandardClass();
    }

    private boolean noRootClassConfigProvided(TestConfig testConfig) {
        return !testConfig.useTestClassAsRoot() && !testConfig.findRootClassOnClasspath() &&
                rootClassFieldNotProvided(testConfig);
    }

    private boolean rootClassFieldNotProvided(TestConfig testConfig) {
        if(testConfig.rootClass() == null) {
            return true;
        }
        return Objects.equals(testConfig.rootClass(), RootClassSettings.ROOT_CLASS_NOT_DIRECTLY_SPECIFIED);
    }

    public static RootClassFinder createDefaultRootClassFinder() {
        ClassGraph classGraph = new ClassGraph()
                .enableClassInfo()
                .enableAnnotationInfo();
        return new RootClassFinder(classGraph);
    }

}
