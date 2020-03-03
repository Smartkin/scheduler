package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.HomeworkDto;
import com.dvoeizlarza.scheduler.entity.Homework;
import com.dvoeizlarza.scheduler.repository.HomeworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class HomeworkService /*implements CRLUD<Homework, HomeworkDto>*/ {
    private HomeworkRepository homeworkRepository;
    private DisciplineService disciplineService;
    private FileService fileService;

    public Homework create(HomeworkDto dto) {
        return modify(null, dto);
    }

    public Homework read(Long id) {
        return homeworkRepository.findById(id).orElse(null);
    }

    public List<Homework> readList(Long discId, boolean upToDate) {
        if (!upToDate) {
            return homeworkRepository.findByDisciplineId(discId);
        } else {
            return homeworkRepository.findByDisciplineIdAndUpToDate(discId, LocalDate.now());
        }
    }

    public Homework modify(Long id, HomeworkDto dto) {
        Homework homework = new Homework();
        homework.setId(dto.getId());
        homework.setText(dto.getText());
        homework.setDate(dto.getDate());
        homework.setDiscipline(disciplineService.read(dto.getDiscipline()));
        homeworkRepository.save(homework);
        for (MultipartFile f : dto.getFiles()) {
            fileService.create(homework, f);
        }
        return homework;
    }

    public Homework delete(Long id) {
        return null;
    }

    @Autowired
    public void setHomeworkRepository(HomeworkRepository homeworkRepository) {
        this.homeworkRepository = homeworkRepository;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
    }
}
