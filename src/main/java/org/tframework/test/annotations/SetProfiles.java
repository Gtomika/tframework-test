/* Licensed under Apache-2.0 2024. */
package org.tframework.test.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to specify profiles to be set into the
 * applications {@link org.tframework.core.profiles.ProfilesContainer}. For example, to set
 * the {@code test} and {@code dev} profiles for the given tests, use:
 * <pre>
 * {@literal @SetProfiles({"test", "dev"})}
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SetProfiles {

    /**
     * Specifies the profiles to be set.
     */
    String[] value();

}
