package com.s2p.repository;

import com.s2p.model.HolidayMailLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HolidayMailLogRepository
        extends JpaRepository<HolidayMailLog, Long> {

    boolean existsByUserIdAndHolidayIdAndSentDate(
            Long userId, Long holidayId, LocalDate sentDate);
}