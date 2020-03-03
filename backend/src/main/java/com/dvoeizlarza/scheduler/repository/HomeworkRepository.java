package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    @Query(value = "select h from Homework h, Discipline d where h.discipline=d AND d.id=?1")
    List<Homework> findByDisciplineId(Long discId);
    @Query(value = "select h from Homework h, Discipline d where h.discipline=d AND d.id=?1 AND h.date>=?2")
    List<Homework> findByDisciplineIdAndUpToDate(Long discId, LocalDate date);
}
