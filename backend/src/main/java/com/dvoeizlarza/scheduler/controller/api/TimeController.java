package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.service.TimeService;
import com.dvoeizlarza.scheduler.viewconverter.TimeViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@Controller
@RequestMapping("api/time")
public class TimeController {
    private TimeService timeService;
    private TimeViewConverter timeViewConverter;

    @PostMapping("create")
    @ResponseBody
    void create(@RequestParam Long schId,
                @RequestParam String name,
                @RequestParam LocalTime begin,
                @RequestParam LocalTime end){
        timeService.create(null, schId, name, begin, end);
    }

    @GetMapping
    @ResponseBody
    public Object read(Long id){
        Time time = timeService.read(id);
        return timeViewConverter.convert(time);
    }

    @GetMapping("list")
    @ResponseBody
    public Object readList(Long schid){
        return timeService.readList(schid).stream().map(timeViewConverter::convert);
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
