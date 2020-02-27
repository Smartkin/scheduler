package com.dvoeizlarza.scheduler.controller.api;

import com.dvoeizlarza.scheduler.dto.TypeDto;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.service.TypeService;
import com.dvoeizlarza.scheduler.viewconverter.TypeViewConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("api/type")
public class TypeController {
    private TypeService typeService;
    private TypeViewConverter typeViewConverter;

    @PostMapping("create")
    @ResponseBody
    HttpEntity<String> create(@RequestBody TypeDto dto) {
        typeService.create(dto);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    @ResponseBody
    Object read(@RequestParam Long id){
        Type type = typeService.read(id);
        return typeViewConverter.convert(type);
    }

    @GetMapping("list")
    @ResponseBody
    Object readList(@RequestParam Long schId){
        List<Type> types = typeService.readList(schId);
        return types.stream().map(typeViewConverter::convert);
    }

    @PostMapping("modify")
    @ResponseBody
    HttpEntity<String> modify(@RequestParam Long id, @RequestBody TypeDto dto){
        typeViewConverter.convert(typeService.modify(id, dto));
        return ResponseEntity.ok("Success");
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTypeViewConverter(TypeViewConverter typeViewConverter) {
        this.typeViewConverter = typeViewConverter;
    }
}
