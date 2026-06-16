package com.mycodeyatra.tests;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
public class WireMockTest {
    private WireMockServer wireMockServer;
    @BeforeClass
    public void setup() {
        // Start WireMock on an isolated port
        wireMockServer = new WireMockServer(8089);
        wireMockServer.start();
        // Point RestAssured to the mock server instead of the real internet!
        RestAssured.baseURI = "http://localhost:8089";
        // Stub a mock API endpoint
        wireMockServer.stubFor(get(urlEqualTo("/third-party/payment-gateway"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("{ \\"status\\": \\"SUCCESS\\", \\"transactionId\\": \\"12345ABC\\" }")));
    }