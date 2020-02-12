package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisciplineRepository extends JpaRepository<Discipline, Long> {
    List<Discipline> findByScheduleId(Long schId);

}
