package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.entity.LessonDate;
import com.dvoeizlarza.scheduler.service.LessonDateService;
import com.dvoeizlarza.scheduler.view.LessonDateView;
import com.dvoeizlarza.scheduler.viewconverter.LessonDateViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/lessonDate")
public class LessonDateController {
    private LessonDateService lessonDateService;
    private LessonDateViewConverter lessonDateViewConverter;

    @GetMapping
    @ResponseBody
    LessonDateView read(Long id){
        return lessonDateViewConverter.convert(lessonDateService.read(id));
    }

    @GetMapping("list")
    @ResponseBody
    List<LessonDateView> readList(@RequestParam Long schId, @RequestParam String date, @RequestParam int count){
        List<LessonDate> lessonDates = lessonDateService.readList(schId, LocalDate.parse(date), count);
        return lessonDates.stream()
                .map(lessonDateViewConverter::convert)
                .sorted(LessonDateView.COMPARE_BY_START)
                .collect(Collectors.toList());
    }

    @Autowired
    public void setLessonDateService(LessonDateService lessonDateService) {
        this.lessonDateService = lessonDateService;
    }

    @Autowired
    public void setLessonDateViewConverter(LessonDateViewConverter lessonDateViewConverter) {
        this.lessonDateViewConverter = lessonDateViewConverter;
    }
}
