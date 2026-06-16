// src/test/java/com/mycodeyatra/karate/setup/SetupRunner.java
package com.mycodeyatra.karate.setup;
import com.intuit.karate.junit5.Karate;
class SetupRunner {
    @Karate.Test
    Karate testSetup() {
        return Karate.run("setup").relativeTo(getClass());
    }
}