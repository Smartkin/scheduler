package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import com.dvoeizlarza.scheduler.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class TimeService {
    private TimeRepository timeRepository;
    private ScheduleRepository scheduleRepository;

    public Time create(Long id, Long schId, String name, LocalTime begin, LocalTime end){
        Schedule schedule = scheduleRepository.findById(schId).orElse(null);
        if(schedule==null){
            return null;
        }
        Time time = new Time();
        time.setSchedule(schedule);
        time.setId(id);
        time.setBegin(begin);
        time.setEnd(end);
        time.setName(name);
        timeRepository.save(time);
        return time;
    }

    public Time read(Long id){
        return timeRepository.findById(id).orElse(null);
    }

    public List<Time> readList(Long schId){
        return timeRepository.findByScheduleId(schId);
    }

    @Autowired
    public void setTimeRepository(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
