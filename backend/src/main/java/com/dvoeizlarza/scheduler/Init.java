package com.dvoeizlarza.scheduler;

import com.dvoeizlarza.scheduler.dto.*;
import com.dvoeizlarza.scheduler.entity.*;
import com.dvoeizlarza.scheduler.enums.CertificationType;
import com.dvoeizlarza.scheduler.enums.NoteType;
import com.dvoeizlarza.scheduler.enums.Role;
import com.dvoeizlarza.scheduler.enums.WeekType;
import com.dvoeizlarza.scheduler.repository.UsrRepository;
import com.dvoeizlarza.scheduler.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    @Autowired
    LessonDateService lessonDateService;
    @Autowired
    UsrRepository usrRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    void init() {
        if(!scheduleService.readList().isEmpty()){
            return;
        }
        ScheduleDto scheduleDto = new ScheduleDto(null, "МТУСИ", "ИТ", "БСТ1602", "8", LocalDate.of(2020,2,5), LocalDate.of(2020,4,30));
        Schedule schedule = scheduleService.create(scheduleDto);
        DisciplineDto disciplineDto = new DisciplineDto(null, 1L, "Text", null, CertificationType.Nope);
//        Discipline discipline = disciplineService.create(disciplineDto);
        TeacherDto teacherDto = new TeacherDto(null, 1L, "Крот", "Препод");
        List<TeacherDto> teacherDtos = new LinkedList<>();
        teacherDtos.add(teacherDto);
        TypeDto typeDto = new TypeDto(null, 1L, "Лекция");
        TimeDto timeDto = new TimeDto(null, 1L, "Первая пара", LocalTime.of(9,30), LocalTime.of(11, 5));
        LessonDto lessonDto = new LessonDto(WeekType.Any, DayOfWeek.MONDAY, LocalDate.of(2020,2,4), LocalDate.of(2020,4,30),null, 1L, teacherDtos, disciplineDto, typeDto, timeDto, "A-321", "");
        lessonService.create(lessonDto);
        NoteDto noteDto1 = new NoteDto(null, 2L, NoteType.Auditorium, "E-44", "Изменение аудитории");
        lessonDateService.modify(noteDto1);
        NoteDto noteDto2 = new NoteDto(null, 2L, NoteType.Discipline, "TEXT", "Теперь предмет капсом");
        lessonDateService.modify(noteDto2);
        NoteDto noteDto3 = new NoteDto(null, 2L, NoteType.Auditorium, "E-99", "Ещё раз аудиторию");
        lessonDateService.modify(noteDto3);
        teacherService.delete(1L);

        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        USR a_sey = new USR(null, "a-sey", passwordEncoder.encode("USER"), true, roles, null, null);
        usrRepository.save(a_sey);
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
}
