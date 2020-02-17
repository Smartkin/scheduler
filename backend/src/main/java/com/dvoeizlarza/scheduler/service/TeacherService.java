package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Teacher;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import com.dvoeizlarza.scheduler.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {
    private TeacherRepository teacherRepository;
    private ScheduleRepository scheduleRepository;

    public Teacher create(Long scheduleId, String name, String info){
        Schedule schedule = scheduleRepository.findById(scheduleId).orElse(null);
        if(schedule == null){
            return null;
        }
        Teacher teacher = new Teacher();
        teacher.setSchedule(schedule);
        teacher.setName(name);
        teacher.setInfo(info);
        teacherRepository.save(teacher);
        return teacher;
    }

    public List<Teacher> readList(Long schId){
        return teacherRepository.findByScheduleId(schId);
    }

    public Teacher read(Long id){
        return teacherRepository.findById(id).orElse(null);
    }

    @Autowired
    public void setTeacherRepository(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
