package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.TeacherDto;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Teacher;
import com.dvoeizlarza.scheduler.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService implements CRLUD<Teacher, TeacherDto> {
    private ScheduleService scheduleService;
    private TeacherRepository teacherRepository;

    public Teacher create(TeacherDto dto) {
        return modify(null, dto);
    }

    public Teacher read(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    public Teacher readOrCreate(TeacherDto dto) {
        if (dto == null) {
            return null;
        }
        Teacher teacher = null;
        if (dto.getId() != null) {
            teacher = read(dto.getId());
            if (!teacher.getSchedule().getId().equals(dto.getSchId())) {
                teacher = null;
            }
        }
        if (teacher == null) {
            teacher = create(dto);
        }
        return teacher;
    }

    public List<Teacher> readList(Long schId) {
        return teacherRepository.findByScheduleId(schId);
    }

    public Teacher modify(Long id, TeacherDto dto) {
        Schedule schedule = scheduleService.read(dto.getSchId());
        if (schedule == null) {
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setId(id);
        teacher.setSchedule(schedule);
        teacher.setName(dto.getName());
        teacher.setInfo(dto.getInfo());
        teacherRepository.save(teacher);
        return teacher;
    }

    public Teacher delete(Long id) {
        try {
            teacherRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            return read(id);
        }
        return null;
    }

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
