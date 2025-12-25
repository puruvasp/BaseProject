package com.s2p.schedular;

import com.s2p.service.HolidayNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class HolidayScheduler {

    @Autowired
    private HolidayNotificationService service;

    // Runs daily at 12:00 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void runAtMidnight() {
        service.sendHolidayMailsIfAny();
    }
}
