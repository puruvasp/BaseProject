package com.s2p.repository;

import com.s2p.model.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HolidayRepository extends JpaRepository<Holiday, Long> {

    @Query("""
        SELECT h FROM Holiday h
        WHERE :today BETWEEN h.startDate AND h.endDate
    """)
    List<Holiday> findActiveHolidays(LocalDate today);
}
