package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.ScheduleDto;
import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService implements CRLUD<Schedule, ScheduleDto> {
    private ScheduleRepository scheduleRepository;

    /**
     * Создание расписания
     */
    public Schedule create(ScheduleDto dto){
        return modify(null, dto);
    }

    /**
     * Получение конкретного расписания
     * @param id - Номер расписания
     * @return - Объект расписания Schedule
     */
    public Schedule read(Long id){
        return scheduleRepository.findById(id).orElse(null);
    }

    /**
     * Получение списка всех расписаний
     * @return - Список из объектов расписания Schedule
     */
    public List<Schedule> readList(Long schId){
        return readList();
    }

    public List<Schedule> readList(){
        return scheduleRepository.findAll();
    }

    public Schedule modify(Long id, ScheduleDto dto){
        Schedule schedule = new Schedule();
        schedule.setId(id);
        schedule.setUniversity(dto.getUniversity());
        schedule.setFaculty(dto.getFaculty());
        schedule.setGroupName(dto.getGroupName());
        schedule.setSemester(dto.getSemester());
        schedule.setStart(dto.getStart());
        schedule.setStop(dto.getStop());
        scheduleRepository.save(schedule);
        return schedule;
    }

    public Schedule delete(Long id) {
        try {
            scheduleRepository.deleteById(id);
        }catch (DataIntegrityViolationException e){
            return read(id);
        }
        return null;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
