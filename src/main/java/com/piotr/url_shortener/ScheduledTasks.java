package com.piotr.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;

@Component
public class ScheduledTasks {

    private LinkServiceImpl service;
    private Clock clock;

    @Autowired
    ScheduledTasks(LinkServiceImpl service,Clock clock){
        this.service = service;
        this.clock = clock;
    }

    @Scheduled(cron = "0 0 */12 * * *") // Execute every 12 hours
    @Async
    public void task2() throws InterruptedException {
        service.deleteOldRecords(clock.instant().minus(Duration.ofDays(1)));// deletes older than 1 day
    }
}