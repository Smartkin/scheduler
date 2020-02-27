package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.ScheduleDto;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.service.ScheduleService;
import com.dvoeizlarza.scheduler.viewconverter.ScheduleViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/schedule")
public class ScheduleController {
    private ScheduleService scheduleService;
    private ScheduleViewConverter scheduleViewConverter;

    @PostMapping("create")
    ResponseEntity<String> create(@RequestBody ScheduleDto scheduleDto) {
        scheduleService.create(scheduleDto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    @ResponseBody
    Object read(@RequestParam(name = "id", defaultValue = "") Long id) {
        if (id == null) {
            return scheduleService.readList().stream().map(scheduleViewConverter::convert);
        }
        Schedule schedule = scheduleService.read(id);
        return scheduleViewConverter.convert(schedule);
    }

    @PostMapping("modify")
    @ResponseBody
    ResponseEntity<String> modify(Long id, ScheduleDto dto) {
        scheduleViewConverter.convert(scheduleService.modify(id, dto));
        return ResponseEntity.ok("Success");
    }

    @Autowired
    public void setScheduleViewConverter(ScheduleViewConverter scheduleViewConverter) {
        this.scheduleViewConverter = scheduleViewConverter;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}