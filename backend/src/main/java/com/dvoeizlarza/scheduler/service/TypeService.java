package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.TypeDto;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    private TypeRepository typeRepository;
    private ScheduleService scheduleService;

    public Type create(TypeDto dto){
        return modify(null, dto);
    }

    public Type read(Long id){
        return typeRepository.findById(id).orElse(null);
    }

    public List<Type> readList(Long schId){
        return typeRepository.findByScheduleId(schId);
    }

    public Type modify(Long id, TypeDto dto){
        Schedule schedule = scheduleService.read(dto.getSchId());
        if(schedule==null){
            return null;
        }
        Type type = new Type();
        type.setId(id);
        type.setSchedule(schedule);
        type.setName(dto.getName());
        typeRepository.save(type);
        return type;
    }

    public Type delete(Long id){
        return null;
    }

    @Autowired
    public void setTypeRepository(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
