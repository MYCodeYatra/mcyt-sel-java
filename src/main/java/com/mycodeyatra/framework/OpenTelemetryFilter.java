package com.mycodeyatra.framework;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.TraceFlags;
import io.opentelemetry.api.trace.TraceState;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import java.util.UUID;
public class OpenTelemetryFilter implements Filter {
    @Override
    public Response filter(FilterableRequestSpecification requestSpec, 
                           FilterableResponseSpecification responseSpec, 
                           FilterContext ctx) {
        // 1. Generate a unique 32-character Trace ID for this specific test request
        String traceId = UUID.randomUUID().toString().replace("-", "") + 
                         UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // 2. Generate a 16-character Span ID
        String spanId = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        // 3. Format according to W3C Trace Context (00-traceid-spanid-01)
        String traceparent = String.format("00-%s-%s-01", traceId, spanId);
        // 4. Inject the header into the RestAssured Request
        requestSpec.header("traceparent", traceparent);
        System.out.println("Injected W3C Trace Context: " + traceparent);
        // Continue executing the request
        return ctx.next(requestSpec, responseSpec);
    }
}