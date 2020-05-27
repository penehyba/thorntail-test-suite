package org.wildfly.swarm.ts.microprofile.health.check.v22;

import java.io.IOException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.client.fluent.Request;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wildfly.swarm.arquillian.DefaultDeployment;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Arquillian.class)
@DefaultDeployment
public class MicroProfileHealthCheck22Test {
    // TODO 2.7.0
    // https://github.com/eclipse/microprofile-health/releases/tag/2.2
    // https://github.com/eclipse/microprofile-health/milestone/4?closed=1

    private static final String URL_HEALTH = "http://localhost:8080/health";

    @Test
    @RunAsClient
    public void overall() throws IOException {
        String response = Request.Get(URL_HEALTH).execute().returnContent().asString();
        // expected check count is 2 because SimplifiedHealthCheck contains both annotations: @Liveness and @Readiness
        checkResponse(response, 2, "simplified");
    }

    private void checkResponse(String response, int checkCount, String... names) {
        JsonElement json = JsonParser.parseString(response);
        assertThat(json.isJsonObject()).isTrue();
        assertThat(json.getAsJsonObject().has("status")).isTrue();
        assertThat(json.getAsJsonObject().get("status").getAsString()).isEqualTo("UP");
        assertThat(json.getAsJsonObject().has("checks")).isTrue();
        JsonArray checks = json.getAsJsonObject().getAsJsonArray("checks");
        assertThat(checks.size()).isEqualTo(checkCount);
        for (JsonElement checkElement : checks.getAsJsonArray()) {
            checkCheckElement(checkElement, names);
        }
    }

    private void checkCheckElement(JsonElement checkElement, String... expectedNames) {
        JsonObject check = checkElement.getAsJsonObject();
        assertThat(check.has("name")).isTrue();

        String actualName = check.getAsJsonObject().get("name").getAsString();
        assertThat(expectedNames).contains(actualName);
        assertThat(check.has("status")).isTrue();
        assertThat(check.get("status").getAsString()).isEqualTo("UP");
        assertThat(check.has("data")).isFalse();
    }
}
