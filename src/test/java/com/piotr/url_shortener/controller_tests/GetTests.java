package com.piotr.url_shortener.controller_tests;

import com.piotr.url_shortener.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UrlShortenerApplication.class)
@AutoConfigureMockMvc
@EnableTestcontainers
class GetTests {

    public static final String DEFAULT_ORIGINAL_BODY = "{\"originalLink\":\"dlugi link\"}";
    public static final String DEFAULT_ORIGINAL_BODY2 = "{\"originalLink\":\"dlugi link2\"}";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LinksRepository repository;

    @AfterEach
    public void cleanUp() {
        repository.deleteAll();
    }

    @Test
    public void givenIncorrectFormatLink_whenGetLink_thenStatus400()
            throws Exception {
        mvc.perform(get("/randomID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenIncorrectLink_whenGetLink_thenStatus404()
            throws Exception {
        mvc.perform(get("/info/75a3593e-9c7b-4078-958c-47e806b6c170")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenNoLink_whenGetall_thenStatus200()
            throws Exception {
        mvc.perform(get("/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenLink_whenGetLink_thenStatus200()
            throws Exception {
        UUID shortenedLink = APIRequests.post(mvc, DEFAULT_ORIGINAL_BODY);

        mvc.perform(get("/info/" + shortenedLink)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.originalLink").value("dlugi link"))
                .andExpect(jsonPath("$.shortenedLink").value((shortenedLink).toString()));
    }

    @Test
    public void givenLinks_whenGetAll_thenStatus200()
            throws Exception {
        UUID shortenedLink = APIRequests.post(mvc, DEFAULT_ORIGINAL_BODY);
        UUID shortenedLink2 = APIRequests.post(mvc, DEFAULT_ORIGINAL_BODY2);

        mvc.perform(get("/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].originalLink").value("dlugi link"))
                .andExpect(jsonPath("$[0].shortenedLink").value(shortenedLink.toString()))
                .andExpect(jsonPath("$[1].originalLink").value("dlugi link2"))
                .andExpect(jsonPath("$[1].shortenedLink").value(shortenedLink2.toString()));
    }

    @Test
    public void givenLink_whenRedirect_thenStatus302()
            throws Exception {
        UUID shortenedLink = APIRequests.post(mvc, DEFAULT_ORIGINAL_BODY);

        mvc.perform(get("/" + shortenedLink)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isFound());
    }

    @Test
    public void givenIncorrectFormatLink_whenRedirect_thenStatus400()
            throws Exception {
        mvc.perform(get("/randomID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenIncorrectLink_whenRedirect_thenStatus404()
            throws Exception {
        mvc.perform(get("/75a3593e-9c7b-4078-958c-47e806b6c170")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
