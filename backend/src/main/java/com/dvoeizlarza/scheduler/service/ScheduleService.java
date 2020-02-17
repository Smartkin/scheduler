package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {
    private ScheduleRepository scheduleRepository;

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
    public List<Schedule> readAll(){
        return scheduleRepository.findAll();
    }

    /**
     * Создание расписания
     * @param university - ВУЗ
     * @param faculty - Факультет
     * @param group - Группа
     * @param semester - Семестр
     */
    public Schedule create(String university, String faculty, String group, String semester){
        Schedule schedule = new Schedule();
        schedule.setUniversity(university);
        schedule.setFaculty(faculty);
        schedule.setGroupName(group);
        schedule.setSemester(semester);
        schedule.setStart(LocalDate.now());
        scheduleRepository.save(schedule);
        return schedule;
    }

    @Autowired
    public void setScheduleRepository(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }
}
