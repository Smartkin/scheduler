package com.dvoeizlarza.scheduler;

import com.dvoeizlarza.scheduler.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("delme")
public class DelMeController {
    @Autowired
    FileService fileService;
    @PostMapping("create")
    void create(@RequestParam("file") MultipartFile file){
        fileService.create(null, file);
    }

    @GetMapping
    @ResponseBody
    void read(@RequestParam Long id, HttpServletResponse response){
        try {
            response.getOutputStream().write(fileService.read(id));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
