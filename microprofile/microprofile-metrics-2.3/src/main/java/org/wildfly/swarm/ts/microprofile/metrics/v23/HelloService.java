package org.wildfly.swarm.ts.microprofile.metrics.v23;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.metrics.Counter;
import org.eclipse.microprofile.metrics.Metadata;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricType;

@ApplicationScoped
public class HelloService {
    @Inject
    private MetricRegistry metrics;

    private Counter counter;

    @PostConstruct
    private void setupMetrics() {
        counter = metrics.counter(Metadata.builder()
                .withName("hello-count")
                .withType(MetricType.COUNTER)
                .withDisplayName("Hello Count")
                .withDescription("Number of hello invocations")
                .reusable(true)
                .build());
    }

    public String hello()  {
        counter.inc();
        return "Hello from programmatically counted method";
    }
}
