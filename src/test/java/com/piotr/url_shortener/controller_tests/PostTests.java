package com.piotr.url_shortener.controller_tests;

import com.piotr.url_shortener.EnableTestcontainers;
import com.piotr.url_shortener.LinkServiceImpl;
import com.piotr.url_shortener.UrlShortenerApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UrlShortenerApplication.class)
@AutoConfigureMockMvc
@EnableTestcontainers
public class PostTests {

    public static final String DEFAULT_ORIGINAL_LINK = "dlugi link";
    @Autowired
    private MockMvc mvc;

    @Test
    public void givenCorrectBody_whenCreateLink_thenStatus201()
            throws Exception {
        mvc.perform(post("/saveLink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalLink\":\"" + DEFAULT_ORIGINAL_LINK + "\"}")
                        .characterEncoding("utf-8"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void givenIncorrectShortenedLinkField_whenCreateLink_thenStatus400()
            throws Exception {
        mvc.perform(post("/saveLink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalLink\":\"" + DEFAULT_ORIGINAL_LINK + "\",\"shortenedLink\":\"krutki\"}")
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void givenShortenedLinkField_whenCreateLink_thenStatus404()
            throws Exception {
        mvc.perform(post("/saveLink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalLink\":\"" + DEFAULT_ORIGINAL_LINK + "\",\"shortenedLink\":\"75a3593e-9c7b-4078-958c-47e806b6c170\"}")
                        .characterEncoding("utf-8"))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void givenIncorrectBody_whenCreateLink_thenStatus400()
            throws Exception {
        mvc.perform(post("/saveLink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"originalLink\":\"" + DEFAULT_ORIGINAL_LINK + "\",\"shortenedLink\":\"krutki\",}")
                        .characterEncoding("utf-8"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
