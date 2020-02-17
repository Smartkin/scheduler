package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.LessonDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonDateRepository extends JpaRepository<LessonDate, Long> {
}
