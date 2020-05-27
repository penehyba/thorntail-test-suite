package org.wildfly.swarm.ts.microprofile.fault.tolerance.v21;

import java.io.IOException;

import org.apache.http.client.fluent.Request;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment
public class FaultTolerance21AsyncTest {
    // TODO 2.7.0
    // https://download.eclipse.org/microprofile/microprofile-fault-tolerance-2.1/microprofile-fault-tolerance-spec.html#release_notes_21

    @Test
    @RunAsClient
    public void timeoutOkCompletionStage() throws IOException {
        String response = Request.Get("http://localhost:8080/async?operation=timeout").execute().returnContent().asString();
        assertThat(response).isEqualTo("Hello from @Timeout method");
    }
}
