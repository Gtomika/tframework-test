# TFramework Test

These are the testing tools of the [TFramework](https://github.com/Gtomika/tframework-core).

```gradle
repositories {
     ...
     maven {
        url "https://jitpack.io"
     }
}

dependencies {
    implementation "com.github.gtomika:tframework-core:${tframeworkCoreVersion}"
    testImplementation "com.github.gtomika:tframework-test:${tframeworkTestVersion}"
}
```

# Junit 5

There is support for JUnit 5 with an extension that is customizable with annotations, or programmatically.

## Launch your application in tests

You may use `@ExtendWith(TFrameworkExtension.class)` directly, however, there are some quirks that must be followed. To spare
yourself of these, it's recommended to use either `@TFrameworkTest` or `@IsolatedTFrameworkTest`.

To start your TFramework application for testing, use `@TFrameworkTest` on the test class. This will look up your
app from the classpath, launch it before the tests, and close it after all tests are completed.

```java
@SetProfiles("test")
@TFrameworkTest //contains @ExtendWith(TFrameworkExtension.class) and useful configurations
public class TframeworkTestAnnotationTest {

    @Test
    public void shouldLaunchApplication(@InjectElement Application application) {
        TFrameworkAssertions.assertHasProfile(application.getProfilesContainer(), "test");
    }
}
```

The example above also shows a few other things:

- Customization options via annotations such as `SetProfiles`. For all options, check the
[annotations package](./src/main/java/org/tframework/test/commons/annotations).
- Injecting elements and properties is possible into the test methods. In the example above, the `Application`
element was injected, and asserted on.

You may also use programmatic extension activation with JUnit5's `@RegisterExtension` annotation. An example can be
seen in [this file](./src/test/java/org/tframework/test/junit5/TFrameworkExtensionProgrammaticTest.java).

## Use the application in tests

After the application is up and running in the tests, there are several ways to access its elements and properties. If
your test cases require some elements/properties, these can be injected into the `@Test`, `@BeforeEach` and `@AfterEach` methods:

```java
@TFrameworkTest //contains @ExtendWith(TFrameworkExtension) and useful configurations
public class TframeworkTestAnnotationTest {

    @Test
    public void shouldLaunchApplication(
            @InjectElement Application application,
            @InjectProperty("my.cool.prop") String myCoolProp
    ) {
        //make assertions
    }
}
```

**Additionally, the test class itself will be an element.** So any elements can be constructor or
field injected into it:

```java
@SetProperties("my.cool.prop=test")
@TFrameworkTest
public class TFrameworkTestConstructorInjectionTest {

    private final PropertiesContainer propertiesContainer;

    //framework will use this constructor to create test instance
    public TFrameworkTestConstructorInjectionTest(PropertiesContainer propertiesContainer) {
        this.propertiesContainer = propertiesContainer;
    }

    @Test
    public void shouldLaunchApplication() {
        TframeworkAssertions.assertHasPropertyWithValue(propertiesContainer, "my.cool.prop", "test");
    }

}
```
