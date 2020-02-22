package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.DisciplineDto;
import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.service.DisciplineService;
import com.dvoeizlarza.scheduler.view.DisciplineView;
import com.dvoeizlarza.scheduler.viewconverter.DisciplineViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/discipline")
public class DisciplineController {
    private DisciplineService disciplineService;
    private DisciplineViewConverter disciplineViewConverter;

    @PostMapping("create")
    @ResponseBody
    ResponseEntity create(@RequestBody DisciplineDto dto){
        disciplineViewConverter.convert(disciplineService.create(dto));
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    @ResponseBody
    DisciplineView read(@RequestParam Long id){
        Discipline discipline = disciplineService.read(id);
        return disciplineViewConverter.convert(discipline);
    }

    @GetMapping("list")
    @ResponseBody
    List<DisciplineView> readList(@RequestParam Long schId){
        List<Discipline> disciplines = disciplineService.readList(schId);
        return disciplines.stream().map(disciplineViewConverter::convert).collect(Collectors.toList());
    }

    @PostMapping("modify")
    @ResponseBody
    ResponseEntity modify(@RequestParam Long id, @RequestBody DisciplineDto dto){
        disciplineViewConverter.convert(disciplineService.modify(id, dto));
        return ResponseEntity.ok("Success");
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setDisciplineViewConverter(DisciplineViewConverter disciplineViewConverter) {
        this.disciplineViewConverter = disciplineViewConverter;
    }
}
