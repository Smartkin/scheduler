package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

    public Schedule getSchedule(Long id){
        return scheduleRepository.findById(id).orElse(null);
    }

    public void createSchedule(String university, String faculty, String group, String semester){
        Schedule schedule = new Schedule();
        schedule.setUniversity(university);
        schedule.setFaculty(faculty);
        schedule.setGroupName(group);
        schedule.setSemester(semester);
        scheduleRepository.save(schedule);
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
