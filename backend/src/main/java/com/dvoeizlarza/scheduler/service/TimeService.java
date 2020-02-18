package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.TimeDto;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeService {
    private TimeRepository timeRepository;
    private ScheduleService scheduleService;

    public Time create(TimeDto dto){
        return modify(null, dto);
    }

    public Time read(Long id){
        return timeRepository.findById(id).orElse(null);
    }

    public List<Time> readList(Long schId){
        return timeRepository.findByScheduleId(schId);
    }

    public Time modify(Long id, TimeDto dto){
        Schedule schedule = scheduleService.read(dto.getSchId());
        if(schedule==null){
            return null;
        }
        Time time = new Time();
        time.setId(id);
        time.setSchedule(schedule);
        time.setBegin(dto.getBegin());
        time.setEnd(dto.getEnd());
        time.setName(dto.getName());
        timeRepository.save(time);
        return time;
    }

    public Time delete(Long id){
        return null;
    }

    @Autowired
    public void setTimeRepository(TimeRepository timeRepository) {
        this.timeRepository = timeRepository;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
