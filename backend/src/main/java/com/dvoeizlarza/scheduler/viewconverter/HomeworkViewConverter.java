package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.Homework;
import com.dvoeizlarza.scheduler.service.FileService;
import com.dvoeizlarza.scheduler.view.HomeworkView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HomeworkViewConverter implements Converter<Homework, HomeworkView> {
    private FileService fileService;

    @Override
    public HomeworkView convert(Homework homework) {
        HomeworkView view = new HomeworkView();
        view.setId(homework.getId());
        view.setText(homework.getText());
        view.setFiles(homework.getFiles().stream().map(x-> fileService.read(x.getId())).collect(Collectors.toList()));
        return view;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
