package org.tframework.test.commons.delayers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestDelayersFactory {

    /**
     * Creates a default {@link TestDelayersBundle} with all the default delayers.
     */
    public static TestDelayersBundle createDefaultDelayers() {
        return TestDelayersBundle.of(List.of(
           //TODO
        ));
    }

}
