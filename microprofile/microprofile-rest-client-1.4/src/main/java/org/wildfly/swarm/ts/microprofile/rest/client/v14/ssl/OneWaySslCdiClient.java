package org.wildfly.swarm.ts.microprofile.rest.client.v14.ssl;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "OneWaySslCdiClient")
public interface OneWaySslCdiClient {
    @GET
    @Path("/simple")
    String get();
}
