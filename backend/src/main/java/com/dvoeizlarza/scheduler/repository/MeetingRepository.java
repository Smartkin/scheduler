package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
}
