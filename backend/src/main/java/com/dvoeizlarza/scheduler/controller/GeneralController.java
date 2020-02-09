package com.dvoeizlarza.scheduler.controller;

import com.dvoeizlarza.scheduler.entity.Note;
import com.dvoeizlarza.scheduler.enums.NoteType;
import com.dvoeizlarza.scheduler.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralController {
    private ScheduleService scheduleService;

    @GetMapping
    @ResponseBody
    Object greeting(){
        return scheduleService.getSchedule(1L);
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
