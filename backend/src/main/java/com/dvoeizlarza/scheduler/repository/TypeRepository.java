package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {
    List<Type> findByScheduleId(Long scheduleId);
}
