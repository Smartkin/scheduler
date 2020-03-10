package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.TimeDto;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.service.TimeService;
import com.dvoeizlarza.scheduler.view.TimeView;
import com.dvoeizlarza.scheduler.viewconverter.TimeViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("api/time")
public class TimeController {
    private TimeService timeService;
    private TimeViewConverter timeViewConverter;

    @PostMapping("create")
    @ResponseBody
    HttpEntity<String> create(@RequestBody TimeDto dto){
        timeViewConverter.convert(timeService.create(dto));
        return ResponseEntity.ok("Success");
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
        return timeService.readList(schId).stream().map(timeViewConverter::convert).sorted(TimeView.COMPARE_BY_START).collect(Collectors.toList());
    }

    @PostMapping("modify")
    @ResponseBody
    HttpEntity<String> modify(@RequestParam Long id, @RequestBody TimeDto dto){
        timeViewConverter.convert(timeService.modify(id, dto));
        return ResponseEntity.ok("Success");
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
