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
    implementation "com.github.gtomika:tframework-core:${tframeworkVersion}"
    testImplementation "com.github.gtomika:tframework-test:${tframeworkTestVersion}"
}
```

# Junit 5

There is support for JUnit 5 with an extension that is customizable with annotations.
