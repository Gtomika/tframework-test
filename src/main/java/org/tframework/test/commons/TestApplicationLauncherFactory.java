/* Licensed under Apache-2.0 2024. */
package org.tframework.test.commons;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.tframework.test.commons.utils.RootClassFinder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestApplicationLauncherFactory {

    public static TestApplicationLauncher createTestApplicationLauncher() {
        var rootClassFinder = new RootClassFinder();
        return new TestApplicationLauncher(rootClassFinder);
    }

}
