package dev.jarand.authapi.oauth.rest;

import dev.jarand.authapi.ApiTest;
import dev.jarand.authapi.jaranduser.jarandclient.domain.JarandClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static dev.jarand.authapi.FileUtil.fileAsString;
import static dev.jarand.authapi.JsonMatcherUtil.isPresent;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class OAuthIntegrationTest extends ApiTest {

    @BeforeEach
    void setup() {
        final var client = new JarandClient(UUID.randomUUID(), "someId", "$2a$10$muVmI2xf6IZUJvff8y8ui.rABX5/ivRxi3KttNdXKB6Orw57U8VW2", UUID.randomUUID(), Instant.now());
        when(jarandClientRepository.getClientByClientId("someId")).thenReturn(Optional.of(client));
    }

    @Test
    void POST_token_for_client_credentials_flow_should_return_tokens() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "client_credentials")
                        .param("client_id", "someId")
                        .param("client_secret", "someSecret"))
                .andExpect(status().isOk()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/token.json");
        JSONAssert.assertEquals(
                expectedJson,
                actualJson,
                new CustomComparator(JSONCompareMode.STRICT, isPresent("access_token"), isPresent("refresh_token")));
    }

    @Test
    void POST_token_without_parameters_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(post("/oauth/token")).andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/missing-parameters.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_with_unknown_grant_type_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(post("/oauth/token").param("grant_type", "unknown")).andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/unknown-grant-type.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_client_credentials_flow_without_parameters_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(post("/oauth/token").param("grant_type", "client_credentials")).andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/client-credentials/missing-parameters.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_client_credentials_flow_without_client_id_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "client_credentials")
                        .param("client_secret", "wrongSecret"))
                .andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/client-credentials/missing-client-id.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_client_credentials_flow_without_client_secret_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "client_credentials")
                        .param("client_id", "wrongId"))
                .andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/client-credentials/missing-client-secret.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_refresh_token_flow_without_parameters_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(post("/oauth/token").param("grant_type", "refresh_token")).andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/refresh-token/missing-parameters.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_refresh_token_flow_without_client_id_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "refresh_token")
                        .param("client_secret", "wrongSecret")
                        .param("refresh_token", "someRefreshToken"))
                .andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/refresh-token/missing-client-id.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_refresh_token_flow_without_client_secret_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "refresh_token")
                        .param("client_id", "wrongId")
                        .param("refresh_token", "someRefreshToken"))
                .andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/refresh-token/missing-client-secret.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }

    @Test
    void POST_token_for_refresh_token_flow_without_refresh_token_secret_should_return_error() throws Exception {
        final var mvcResult = mockMvc.perform(
                post("/oauth/token")
                        .param("grant_type", "refresh_token")
                        .param("client_id", "wrongId")
                        .param("client_secret", "wrongSecret"))
                .andExpect(status().isBadRequest()).andReturn();

        final var actualJson = mvcResult.getResponse().getContentAsString();
        final var expectedJson = fileAsString("/token/refresh-token/missing-refresh-token.json");
        JSONAssert.assertEquals(expectedJson, actualJson, true);
    }
}
