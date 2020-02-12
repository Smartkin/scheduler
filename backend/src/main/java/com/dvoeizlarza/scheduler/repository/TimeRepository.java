package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {
    List<Time> findByScheduleId(Long scheduleId);
}
