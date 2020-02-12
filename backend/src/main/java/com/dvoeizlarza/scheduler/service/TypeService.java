package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import com.dvoeizlarza.scheduler.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private TypeRepository typeRepository;
    private ScheduleRepository scheduleRepository;

    public void create(Long schId, Long id, String name){
        Schedule schedule = scheduleRepository.findById(schId).orElse(null);
        if(schedule==null){
            return;
        }
        Type type = new Type();
        type.setId(id);
        type.setSchedule(schedule);
        type.setName(name);
        typeRepository.save(type);
    }

    public Type read(Long id){
        return typeRepository.findById(id).orElse(null);
    }

    public List<Type> readList(Long schId){
        return typeRepository.findByScheduleId(schId);
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
