package com.piotr.url_shortener.controller_tests;

import com.piotr.url_shortener.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UrlShortenerApplication.class)
@AutoConfigureMockMvc
@EnableTestcontainers
public class DeleteTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenLink_whenDeleteLink_thenStatus200()
            throws Exception {
        UUID shortenedLink = APIRequests.post(mvc, "{\"originalLink\":\"dlugi link\"}");

        mvc.perform(delete("/" + shortenedLink)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenIncorrectFormatLink_whenDeleteLink_thenStatus400()
            throws Exception {
        mvc.perform(delete("/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenIncorrectLink_whenDeleteLink_thenStatus404()
            throws Exception {
        mvc.perform(delete("/75a3593e-9c7b-4078-958c-47e806b6c170")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
