package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.LessonDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LessonDateRepository extends JpaRepository<LessonDate, Long> {
    @Query(value = "select ld from LessonDate ld where ld.date=?2 AND ld.schedule.id=?1")
    List<LessonDate> findBySchIdAndDate(Long schId, LocalDate date);
    @Query(value = "select ld from LessonDate ld where ld.date>=?2 AND ld.date<?3 AND ld.schedule.id=?1 order by ld.date")
    List<LessonDate> findBySchIdBetweenDates(Long schId, LocalDate start, LocalDate stop);
}
