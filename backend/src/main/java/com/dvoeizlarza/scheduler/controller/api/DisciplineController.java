package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.enums.CertificationType;
import com.dvoeizlarza.scheduler.service.DisciplineService;
import com.dvoeizlarza.scheduler.viewconverter.DisciplineViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
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
    void create(@RequestParam Long schId,
                  @RequestParam String name,
                  @RequestParam(defaultValue = "") String shortName,
                  @RequestParam(defaultValue = "Nope") CertificationType certificationType){
        disciplineService.create(null, schId, name, shortName, certificationType);
    }

    @GetMapping
    @ResponseBody
    Object read(@RequestParam Long id){
        Discipline discipline = disciplineService.read(id);
        return disciplineViewConverter.convert(discipline);
    }

    @Autowired
    TeacherController teacherController;
    @GetMapping("list")
    @ResponseBody
    Object readList(@RequestParam Long schId){
        List<Discipline> disciplines = disciplineService.readList(schId);
        return disciplines.stream().map(disciplineViewConverter::convert).collect(Collectors.toList());
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
