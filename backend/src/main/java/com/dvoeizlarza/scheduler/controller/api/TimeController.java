package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.DisciplineDto;
import com.dvoeizlarza.scheduler.dto.TimeDto;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.service.TimeService;
import com.dvoeizlarza.scheduler.view.DisciplineView;
import com.dvoeizlarza.scheduler.view.TimeView;
import com.dvoeizlarza.scheduler.viewconverter.TimeViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/time")
public class TimeController {
    private TimeService timeService;
    private TimeViewConverter timeViewConverter;

    @PostMapping("create")
    @ResponseBody
    TimeView create(@RequestBody TimeDto dto){
        return timeViewConverter.convert(timeService.create(dto));
    }

    @GetMapping
    @ResponseBody
    public Object read(@RequestParam Long id){
        Time time = timeService.read(id);
        return timeViewConverter.convert(time);
    }

    @GetMapping("list")
    @ResponseBody
    public Object readList(@RequestParam Long schId){
        return timeService.readList(schId).stream().map(timeViewConverter::convert).collect(Collectors.toList());
    }

    @PostMapping("modify")
    @ResponseBody
    TimeView modify(@RequestParam Long id, @RequestBody TimeDto dto){
        return timeViewConverter.convert(timeService.modify(id, dto));
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Autowired
    public void setTimeViewConverter(TimeViewConverter timeViewConverter) {
        this.timeViewConverter = timeViewConverter;
    }
}
