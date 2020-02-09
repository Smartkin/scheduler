package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
