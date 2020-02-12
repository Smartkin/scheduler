package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.enums.CertificationType;
import com.dvoeizlarza.scheduler.repository.DisciplineRepository;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisciplineService {
    private DisciplineRepository disciplineRepository;
    private ScheduleRepository scheduleRepository;

    //CRud
    public void create(Long id, Long schId, String name, String shortName, CertificationType certificationType){
        Schedule schedule = scheduleRepository.findById(schId).orElse(null);
        if(schedule==null){
            return;
        }
        Discipline discipline = new Discipline();
        discipline.setId(id);
        discipline.setSchedule(schedule);
        discipline.setName(name);
        discipline.setShortName(shortName);
        discipline.setCertificationType(certificationType);
        disciplineRepository.save(discipline);
    }

    public Discipline read(Long id){
        return disciplineRepository.findById(id).orElse(null);
    }

    public List<Discipline> readList(Long schId){
        return disciplineRepository.findByScheduleId(schId);
    }

    @Autowired
    public void setDisciplineRepository(DisciplineRepository disciplineRepository) {
        this.disciplineRepository = disciplineRepository;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
