package com.dvoeizlarza.scheduler;

import com.dvoeizlarza.scheduler.dto.LessonDto;
import com.dvoeizlarza.scheduler.entity.*;
import com.dvoeizlarza.scheduler.enums.CertificationType;
import com.dvoeizlarza.scheduler.enums.WeekType;
import com.dvoeizlarza.scheduler.repository.LessonRepository;
import com.dvoeizlarza.scheduler.repository.NoteRepository;
import com.dvoeizlarza.scheduler.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

@Component
public class Init {
    private ScheduleService scheduleService;

    @Autowired
    DisciplineService disciplineService;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TypeService typeService;
    @Autowired
    TimeService timeService;
    @Autowired
    LessonService lessonService;

    @PostConstruct
    void init() {
        Schedule schedule = scheduleService.create("МТУСИ", "ИТ", "БСТ1602", "8");
        Discipline discipline = disciplineService.create(null, 1L, "Предмет", "D", CertificationType.Nope);
        Teacher teacher = teacherService.create(1L, "Krot", null);
        List<Teacher> teachers = new LinkedList<>();
        teachers.add(teacher);
        Type type = typeService.create(1L, null, "Лекция");
        Time time = timeService.create(null, 1L, "Default", LocalTime.of(9,30), LocalTime.of(11,15));

        LessonDto lessonDto = new LessonDto();
        lessonDto.setSchedule(schedule);
        lessonDto.setDiscipline(discipline);
        lessonDto.setTeacherList(teachers);
        lessonDto.setType(type);
        lessonDto.setTime(time);
        lessonDto.setStartDate(LocalDate.of(2020, 2, 4));
        lessonDto.setEndDate(LocalDate.of(2020, 4, 30));
        lessonDto.setAuditory("A-555");
        lessonDto.setWeekdays(DayOfWeek.MONDAY);
        lessonDto.setWeekType(WeekType.Any);

        lessonService.create(lessonDto);
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
