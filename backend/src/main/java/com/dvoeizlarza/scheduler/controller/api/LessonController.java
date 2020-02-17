package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.entity.Teacher;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.enums.WeekType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("app/lesson")
public class LessonController {
    @PostMapping
    @ResponseBody
    void create(@RequestParam Long schId,
                @RequestParam WeekType weekType,
                @RequestParam DayOfWeek weekdays,
                @RequestParam(defaultValue = "") List<LocalDate> dates,
                @RequestParam Type type,
                @RequestParam LocalTime begTime,
                @RequestParam LocalTime endTime,
                @RequestParam List<Teacher> teachers,
                @RequestParam Discipline discipline,
                @RequestParam String auditory,
                @RequestParam(defaultValue = "") String comment
                ){

    }
}
