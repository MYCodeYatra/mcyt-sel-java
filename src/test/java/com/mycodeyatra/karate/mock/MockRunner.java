// src/test/java/com/mycodeyatra/karate/mock/MockRunner.java
package com.mycodeyatra.karate.mock;
import com.intuit.karate.Results;
import com.intuit.karate.Runner;
import com.intuit.karate.core.MockServer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
class MockRunner {
    @Test
    void testMock() {
        // Spin up the mock server on an open random port!
        MockServer server = MockServer.feature("classpath:.../mock.feature").http(0).build();
        int port = server.getPort();
        // Pass the port into our test feature
        Results results = Runner.path("classpath:.../test-mock.feature")
                .systemProperty("mockPort", port + "")
                .parallel(1);
        assertEquals(0, results.getFailCount());
        server.stop();
    }
}