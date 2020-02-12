package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByScheduleId(Long scheduleId);
}
