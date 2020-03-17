package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.LessonDto;
import com.dvoeizlarza.scheduler.service.LessonService;
import com.dvoeizlarza.scheduler.view.LessonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/lesson")
public class LessonController {
    LessonService lessonService;

    @PostMapping("create")
    @ResponseBody
    ResponseEntity<String> create(@RequestBody LessonDto dto) {
        lessonService.create(dto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    @ResponseBody
    LessonView read(Long id) {
        return lessonService.read(id);
    }

    @PostMapping("modify")
    @ResponseBody
    ResponseEntity<String> modify(Long id, @RequestBody LessonDto dto) {
        lessonService.modify(id, dto);
        return ResponseEntity.ok("Success");
    }

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }
}
