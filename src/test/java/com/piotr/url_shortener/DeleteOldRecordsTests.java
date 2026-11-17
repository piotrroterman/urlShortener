package com.piotr.url_shortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Clock;
import java.time.Instant;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = UrlShortenerApplication.class)
@AutoConfigureMockMvc
@EnableTestcontainers
public class DeleteOldRecordsTests {

    @Autowired
    private LinkServiceImpl service;

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private Clock clock;

    @Test
    public void givenInstantMock_whenNow_thenGetFixedInstantClock() throws Exception {
        Mockito.when(clock.instant()).thenReturn(Instant.parse("2026-09-14T20:30:30.00Z")); // setting current time
        UUID shortenedLink = APIRequests.post(mvc, "{\"originalLink\":\"dlugi link\"}");
        APIRequests.get(mvc, shortenedLink, "dlugi link");

        Mockito.when(clock.instant()).thenReturn(Instant.parse("2026-09-14T20:30:50.00Z")); //current time plus 20 seconds
        UUID shortenedLink2 = APIRequests.post(mvc, "{\"originalLink\":\"dlugi link2\"}");
        APIRequests.get(mvc, shortenedLink2, "dlugi link2");

        Mockito.when(clock.instant()).thenReturn(Instant.parse("2026-09-14T20:30:55.00Z")); //current time plus another 25 seconds
        service.deleteOldRecords(clock.instant().minusSeconds(10));// //current time plus another 15 seconds

        mvc.perform(get("/info/" + shortenedLink)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        mvc.perform(get("/info/" + shortenedLink2)
                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
