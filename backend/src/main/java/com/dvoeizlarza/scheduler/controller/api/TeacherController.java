package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.TeacherDto;
import com.dvoeizlarza.scheduler.entity.Teacher;
import com.dvoeizlarza.scheduler.service.TeacherService;
import com.dvoeizlarza.scheduler.view.TeacherView;
import com.dvoeizlarza.scheduler.viewconverter.TeacherViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("api/teacher")
public class TeacherController {
    private TeacherService teacherService;
    private TeacherViewConverter teacherViewConverter;

    @PostMapping("create")
    @ResponseBody
    ResponseEntity<String> create(@RequestBody TeacherDto dto){
            if (teacherService.create(dto) != null){
                return ResponseEntity.ok("Success");
            }
            return ResponseEntity.badRequest().build();
    }

    @GetMapping
    @ResponseBody
    Object read(@RequestParam Long id){
        Teacher teacher = teacherService.read(id);
        return teacherViewConverter.convert(teacher);
    }

    @GetMapping("list")
    @ResponseBody
    Object readList(@RequestParam Long schId){
        return teacherService.readList(schId).stream().map(teacherViewConverter::convert).sorted(TeacherView.COMPARE_BY_NAME).collect(Collectors.toList());
    }

    @PostMapping("modify")
    @ResponseBody
    ResponseEntity<String> modify(@RequestParam Long id, @RequestBody TeacherDto dto){
        teacherViewConverter.convert(teacherService.modify(id, dto));
        return ResponseEntity.ok("Success");
    }


    @PostMapping("delete")
    @ResponseBody
    ResponseEntity<String> delete(@RequestParam Long id){
        if(teacherService.delete(id)==null){
            return ResponseEntity.ok("Success");
        }else {
            return ResponseEntity.badRequest().body("There are child elements");
        }
    }

    @Autowired
    public void setTeacherViewConverter(TeacherViewConverter teacherViewConverter) {
        this.teacherViewConverter = teacherViewConverter;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }
}
