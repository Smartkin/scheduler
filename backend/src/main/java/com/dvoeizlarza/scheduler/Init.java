package com.dvoeizlarza.scheduler;

import com.dvoeizlarza.scheduler.entity.Note;
import com.dvoeizlarza.scheduler.repository.NoteRepository;
import com.dvoeizlarza.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Init {
    private ScheduleService scheduleService;

    @Autowired
    NoteRepository noteRepository;

    @PostConstruct
    void init() {
        scheduleService.create("МТУСИ", "ИТ", "БСТ1602", "8");
        noteRepository.save(new Note(null, null, null, "aa", "rr"));
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
