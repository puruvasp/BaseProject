package com.s2p.controller;

import com.s2p.service.HolidayNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestMailController {

    @Autowired
    private HolidayNotificationService holidayNotificationService;

    @PostMapping("/send-holiday-mails")
    public String triggerHolidayMails() {
        holidayNotificationService.sendHolidayMailsIfAny();
        return "Holiday mail trigger executed successfully";
    }
}
