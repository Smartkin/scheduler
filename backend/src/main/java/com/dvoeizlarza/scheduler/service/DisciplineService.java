package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.DisciplineDto;
import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.repository.DisciplineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService implements CRLUD{
    private ScheduleService scheduleService;
    private DisciplineRepository disciplineRepository;

    public Discipline create(DisciplineDto dto) {
        return modify(null, dto);
    }

    public Discipline read(Long id){
        return disciplineRepository.findById(id).orElse(null);
    }

    public List<Discipline> readList(Long schId){
        return disciplineRepository.findByScheduleId(schId);
    }


    public Discipline modify(Long id, DisciplineDto dto) {
        DisciplineDto dis = (DisciplineDto) dto;

        Schedule schedule = scheduleService.read(dis.getSchId());
        if(schedule==null){
            return null;
        }
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setSchedule(schedule);
        discipline.setName(dis.getName());
        discipline.setShortName(dis.getShortName());
        discipline.setCertificationType(dis.getCertificationType());
        disciplineRepository.save(discipline);
        return discipline;
    }

    public Object delete(Long id) {
        return null;
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setDisciplineRepository(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }
}
