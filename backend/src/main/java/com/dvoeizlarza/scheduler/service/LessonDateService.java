package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.LessonDate;
import com.dvoeizlarza.scheduler.entity.Note;
import com.dvoeizlarza.scheduler.repository.LessonDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonDateService {
    private LessonDateRepository lessonDateRepository;

    public LessonDate read(Long id) {
        return lessonDateRepository.findById(id).orElse(null);
    }

    public List<LessonDate> readList(Long schId, LocalDate date, int count) {
        List<LessonDate> lessonDates = lessonDateRepository.findBySchIdBetweenDates(schId, date, date.plusDays(count));
        return lessonDates;
    }

    public Note modify(Long id) {
        return null;
    }

    public LessonDate delete(Long id) {
        return null;
    }

    @Autowired
    public void setLessonDateRepository(LessonDateRepository lessonDateRepository) {
        this.lessonDateRepository = lessonDateRepository;
    }
}
