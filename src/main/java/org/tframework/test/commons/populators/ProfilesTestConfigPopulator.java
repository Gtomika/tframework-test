/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons.populators;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.tframework.core.reflection.annotations.AnnotationScanner;
import org.tframework.test.commons.annotations.SetProfiles;
import org.tframework.test.commons.TestConfig;

@Slf4j
public class ProfilesTestConfigPopulator implements TestConfigPopulator {

    private final AnnotationScanner annotationScanner;

    ProfilesTestConfigPopulator(AnnotationScanner annotationScanner) {
        this.annotationScanner = annotationScanner;
    }

    @Override
    public void populateConfig(TestConfig.TestConfigBuilder configBuilder, Class<?> testClass) {
        Set<String> profiles = new HashSet<>();
        annotationScanner.scan(testClass, SetProfiles.class).forEach(profilesAnnotation -> {
            var profilesOnAnnotation = Arrays.asList(profilesAnnotation.value());
            profiles.addAll(profilesOnAnnotation);
        });

        Set<String> existingProfiles = configBuilder.build().profiles();
        if(existingProfiles != null) {
            profiles.addAll(existingProfiles);
        }
        configBuilder.profiles(profiles);
    }
}
