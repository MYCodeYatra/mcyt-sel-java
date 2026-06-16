package com.mycodeyatra.karate.intro;
import com.intuit.karate.junit5.Karate;
class IntroRunner {
    @Karate.Test
    Karate testIntro() {
        return Karate.run("hello").relativeTo(getClass());
    }
}