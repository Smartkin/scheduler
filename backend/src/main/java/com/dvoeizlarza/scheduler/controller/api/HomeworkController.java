package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.HomeworkDto;
import com.dvoeizlarza.scheduler.entity.Homework;
import com.dvoeizlarza.scheduler.service.HomeworkService;
import com.dvoeizlarza.scheduler.viewconverter.HomeworkViewConverter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("api/homework")
public class HomeworkController {
    private HomeworkService homeworkService;
    private HomeworkViewConverter homeworkViewConverter;
    
    @PostMapping("create")
    @ResponseBody
    ResponseEntity<String> create(@RequestBody HomeworkDto dto){
        if (homeworkService.create(dto) != null){
            return ResponseEntity.ok("Success");
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    @ResponseBody
    Object read(@RequestParam Long id){
        Homework homework = homeworkService.read(id);
        return homeworkViewConverter.convert(homework);
    }

    @GetMapping("list")
    @ResponseBody
    Object readList(@RequestParam Long discId, @RequestParam boolean upToDate){
        return homeworkService.readList(discId, upToDate).stream().map(homeworkViewConverter::convert).collect(Collectors.toList());
    }

    @PostMapping("modify")
    @ResponseBody
    ResponseEntity<String> modify(@RequestParam Long id, @RequestBody HomeworkDto dto){
        homeworkViewConverter.convert(homeworkService.modify(id, dto));
        return ResponseEntity.ok("Success");
    }

}
