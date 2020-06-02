package org.wildfly.swarm.ts.microprofile.rest.client.v14;

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
public class MicroProfileRestClient14Test {
    // TODO 2.7.0
    // https://download.eclipse.org/microprofile/microprofile-rest-client-1.4.0/microprofile-rest-client-1.4.0.html#release_notes_14
    // what about version 1.4.1? Check https://issues.redhat.com/browse/THORN-2558 , nothing new

    @Test
    @RunAsClient
    public void configKey() throws IOException {
        String response = Request.Get("http://localhost:8080/rest/client/config-key").execute().returnContent().asString();
        assertThat(response).isEqualTo("Hello from endpoint");
    }
}
