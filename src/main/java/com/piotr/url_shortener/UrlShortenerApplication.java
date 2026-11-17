package com.piotr.url_shortener;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RestController;

@EnableScheduling
@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class UrlShortenerApplication {

    void main() {
        SpringApplication.run(UrlShortenerApplication.class);
    }
}


