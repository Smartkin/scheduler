package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Time;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeRepository extends JpaRepository<Time, Long> {
}
