package org.wildfly.swarm.ts.microprofile.rest.client.v14;

import java.net.URI;
import java.net.URL;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.RestClientBuilder;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/client")
public class RestClientResource {
    private static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";

    private static final String PASSWORD = "client-password";

    @GET
    @Path("/config-key")
    public String configKey() {
        ClientAutoCloseable client = CDI.current().select(ClientAutoCloseable.class, RestClient.LITERAL).get();
        return client.simpleOperation();
    }

    @GET
    @Path("/missing-config-key")
    public String missingConfigKey() {
        try {
            ClientMissingKey client = CDI.current().select(ClientMissingKey.class, RestClient.LITERAL).get();
            return client.simpleOperation();
        } catch (Exception e) {
            return e.getMessage(); // expecting "Neither baseUri nor baseUrl was specified"
        }
    }

    @GET
    @Path("/auto-close")
    public String autoClose() throws Exception {
        String response;
        ClientAutoCloseable client = RestClientBuilder.newBuilder()
                .baseUrl(new URL(HTTP_LOCALHOST_8080))
                .build(ClientAutoCloseable.class);
        try (ClientAutoCloseable c = client) {
            response = c.simpleOperation();
        }
        // now client is auto-closed
        try {
            response += client.simpleOperation();
        } catch (IllegalStateException e) {
            response += ", client was auto-closed as expected.";
        }
        return response;
    }

    @GET
    @Path("/manual-close")
    public String close() throws Exception {
        String response;
        ClientCloseable client = RestClientBuilder.newBuilder()
                .baseUri(new URI(HTTP_LOCALHOST_8080))
                .build(ClientCloseable.class);
        response = client.simpleOperation();
        client.close();
        // now client is closed
        try {
            response += client.simpleOperation();
        } catch (IllegalStateException e) {
            response += ", client was closed as expected.";
        }
        return response;
    }
}
