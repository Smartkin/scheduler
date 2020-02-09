package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
