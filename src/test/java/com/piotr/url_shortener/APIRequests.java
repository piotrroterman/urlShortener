package com.piotr.url_shortener;

import com.jayway.jsonpath.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class APIRequests {
    public static UUID post(MockMvc mvc, String body)
            throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/saveLink")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .characterEncoding("utf-8"))
                .andReturn();

        return UUID.fromString(JsonPath.read(mvcResult.getResponse().getContentAsString(), "$.shortenedLink"));
    }

    public static void get(MockMvc mvc, UUID shortenedLink,String expectedOriginalLink)
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/info/" + shortenedLink)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.originalLink").value(expectedOriginalLink))
                .andExpect(jsonPath("$.shortenedLink").value((shortenedLink).toString()));

    }
}
