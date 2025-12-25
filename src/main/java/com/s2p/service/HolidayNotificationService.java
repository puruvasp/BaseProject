package com.s2p.service;

import com.s2p.model.Holiday;
import com.s2p.model.HolidayMailLog;
import com.s2p.model.User;
import com.s2p.repository.HolidayMailLogRepository;
import com.s2p.repository.HolidayRepository;
import com.s2p.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HolidayNotificationService {

    @Autowired
    private HolidayRepository holidayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HolidayMailLogRepository mailLogRepository;

    @Autowired
    private EmailService emailService;

    public void sendHolidayMailsIfAny() {

        LocalDate today = LocalDate.now();
        List<Holiday> holidays = holidayRepository.findActiveHolidays(today);
        if (holidays.isEmpty()) return;

        List<User> users = userRepository.findAll();

        for (Holiday holiday : holidays) {
            for (User user : users) {

                boolean alreadySent =
                        mailLogRepository.existsByUserIdAndHolidayIdAndSentDate(
                                user.getId(), holiday.getId(), today);

                if (alreadySent) continue;

                String subject = "🎉 " + holiday.getHolidayName() + " Wishes";
                String body =
                        "Dear " + user.getUsername() + ",\n\n" +

                                "Warm greetings from the HR Team!\n\n" +

                                "We are pleased to inform you about the upcoming holiday: " +
                                holiday.getHolidayName() + ". This holiday is a time to relax, " +
                                "recharge, and spend quality moments with your family and loved ones.\n\n" +

                                "📅 Holiday Details:\n" +
                                "• Holiday Name: " + holiday.getHolidayName() + "\n" +
                                "• Holiday Period: " + holiday.getStartDate() + " to " + holiday.getEndDate() + "\n" +

                                holiday.getMessage() + "\n\n" +

                                "We encourage you to plan your time accordingly and make the most of this holiday period. " +
                                "Please ensure that all work responsibilities are managed in advance.\n\n" +

                                "On behalf of the company, we wish you a joyful, safe, and refreshing holiday. " +
                                "May this time bring happiness, good health, and renewed energy.\n\n" +

                                "Best regards,\n" +
                                "Company HR Team";


                emailService.sendMail(user.getEmail(), subject, body);

                HolidayMailLog log = new HolidayMailLog();
                log.setUserId(user.getId());
                log.setHolidayId(holiday.getId());
                log.setSentDate(today);

                mailLogRepository.save(log);
            }
        }
    }
}
