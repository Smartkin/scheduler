package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.LessonDto;
import com.dvoeizlarza.scheduler.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("app/lesson")
public class LessonController {
    LessonService lessonService;

    @PostMapping("create")
    @ResponseBody
    void create(LessonDto dto){
        lessonService.create(dto);
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }
}
