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
package org.tframework.test.commons.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used in conjunction with Tframework tests to specify properties to be set into the
 * applications {@link org.tframework.core.properties.PropertiesContainer}. Specify properties as strings,
 * where the property name and values are separated by {@value org.tframework.core.properties.parsers.PropertyParsingUtils#PROPERTY_NAME_VALUE_SEPARATOR}.
 * For example, to set the {@code cool.prop} and {@code test.prop} properties to {@code cool} and {@code test},
 * respectively, use:
 * <pre>
 * {@literal @SetProperties({"cool.prop=cool", "test.prop=test"})}
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SetProperties {

    /**
     * The raw property string that should be set.
     */
    String[] value();

}
