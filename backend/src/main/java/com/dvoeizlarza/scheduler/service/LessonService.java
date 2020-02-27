package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.*;
import com.dvoeizlarza.scheduler.entity.*;
import com.dvoeizlarza.scheduler.enums.LessonDateStatus;
import com.dvoeizlarza.scheduler.enums.WeekType;
import com.dvoeizlarza.scheduler.repository.*;
import com.dvoeizlarza.scheduler.view.LessonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class LessonService {
    private ScheduleService scheduleService;
    private DisciplineService disciplineService;
    private TeacherService teacherService;
    private TimeService timeService;
    private TypeService typeService;
    private TeachersRepository teachersRepository;
    private TdtRepository tdtRepository;
    private LessonRepository lessonRepository;
    private LessonDateRepository lessonDateRepository;

    private Schedule schedule;
    private Discipline discipline;
    private List<Teacher> teacherList;
    private Time time;
    private Type type;

    public Lesson create(LessonDto dto) {
        return modify(null, dto);
    }

    public LessonView read(Long id) {
        Lesson lesson = lessonRepository.findById(id).orElse(null);
        if (lesson == null) {
            return null;
        }
        LessonView view = new LessonView();
        dateCompile(lesson, view);
        TDT tdt = lesson.getTdt();
        view.setSchId(tdt.getDiscipline().getSchedule().getId());
        view.setAuditory(lesson.getAuditorium());
        view.setDiscipline(tdt.getDiscipline().getId());
        view.setAuditory(lesson.getAuditorium());
        view.setTeacherList(tdt.getTeachers().stream().map(Teachers::getId).collect(Collectors.toList()));
        view.setTime(lesson.getTime().getId());
        view.setType(tdt.getType().getId());
        return view;
    }

    public Lesson modify(Long id, LessonDto dto) {
        postValidate(dto);
        TDT tdt = new TDT();
        tdt.setDiscipline(discipline);
        tdt.setType(type);
        tdtRepository.save(tdt);

        for (Teacher t : teacherList) {
            Teachers teachers = new Teachers();
            teachers.setTdt(tdt);
            teachers.setTeacher(t);
            teachersRepository.save(teachers);
        }

        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setTdt(tdt);
        lesson.setTime(time);
        lesson.setAuditorium(dto.getAuditory());
        lessonRepository.save(lesson);

        if (!dto.getWeekType().equals(WeekType.Dates)) {
            dto.setDates(getDates(dto));
        }

        for (LocalDate d : dto.getDates()) {
            LessonDate lessonDate = new LessonDate();
            lessonDate.setSchedule(schedule);
            lessonDate.setDate(d);
            lessonDate.setLesson(lesson);
            lessonDate.setLessonDateStatus(LessonDateStatus.Planned);
            lessonDateRepository.save(lessonDate);
        }
        return lesson;
    }

    private void postValidate(LessonDto dto) {
        schedule = scheduleService.read(dto.getSchId());
        if (schedule == null) {
            return;
        }
        DisciplineDto disciplineDto = dto.getDiscipline();
        TimeDto timeDto = dto.getTime();
        TypeDto typeDto = dto.getType();
        List<TeacherDto> teachersDto = dto.getTeacherList();
        if (!dto.getSchId().equals(disciplineDto.getSchId()) ||
                !dto.getSchId().equals(timeDto.getSchId()) ||
                !dto.getSchId().equals(typeDto.getSchId())) {
            return;
        }
        for (TeacherDto t : teachersDto) {
            if (!dto.getSchId().equals(t.getSchId())) {
                return;
            }
        }
        discipline = disciplineService.readOrCreate(disciplineDto);
        time = timeService.readOrCreate(timeDto);
        type = typeService.readOrCreate(typeDto);
        teacherList = teachersDto.stream().map(teacherService::readOrCreate).collect(Collectors.toList());
    }

    private List<LocalDate> getDates(LessonDto dto) {
        TemporalField woy = WeekFields.of(Locale.getDefault()).weekBasedYear();
        int defWeek = scheduleService.read(dto.getSchId()).getStart().get(woy);

        List<LocalDate> dates = new LinkedList<>();
        LocalDate date = dto.getStartDate();
        boolean flag = true;
        int step = dto.getWeekType().equals(WeekType.Any) ? 7 : 14;
        while (date.isBefore(dto.getEndDate())) {
            if (flag) {
                if (date.get(woy) - defWeek % 2 == 1 && dto.getWeekType().equals(WeekType.Odd) || date.get(woy) - defWeek % 2 == 0 && dto.getWeekType().equals(WeekType.Even)) {
                    date = date.plusDays(1L);
                    continue;
                }
                if (!date.getDayOfWeek().equals(dto.getWeekdays())) {
                    date = date.plusDays(1L);
                    continue;
                }
                dates.add(date);
                flag = false;
                date = date.plusDays(step);
            } else {
                dates.add(date);
                date = date.plusDays(step);
            }
        }
        return dates;
    }

    private void dateCompile(Lesson lesson, LessonView view) {
        List<LessonDate> lessonDates = lesson.getLessonDates().stream().sorted(LessonDate.COMPARE_BY_DATE).collect(Collectors.toList());
        if (lessonDates.size() == 0) {
            return;
        }
        view.setDates(lessonDates.stream().map(LessonDate::getDate).collect(Collectors.toList()));
        view.setWeekType(WeekType.Dates);
        LessonDate first = lessonDates.get(0);
        LocalDate startDate = first.getDate();
        LocalDate endDate = first.getDate();
        int dist = -1;
        for (LessonDate d : lessonDates) {
            if (dist == -1) {
                if (d.getDate().minusDays(14).equals(endDate)) {
                    dist = 14;
                } else if (d.getDate().minusDays(7).equals(endDate)) {
                    dist = 7;
                } else if (d == first) {
                    continue;
                } else {
                    return;
                }
            } else {
                if (!d.getDate().minusDays(dist).equals(endDate)) {
                    return;
                }
            }
            endDate = d.getDate();
        }
        DayOfWeek dayOfWeek = startDate.getDayOfWeek();

        WeekType weekType;
        if (dist == 7) {
            weekType = WeekType.Any;
        } else {
            TemporalField woy = WeekFields.of(Locale.getDefault()).weekBasedYear();
            int defWeek = scheduleService.read(view.getSchId()).getStart().get(woy);
            boolean parity = (startDate.get(woy) - defWeek) % 2 == 1;
            weekType = parity ? WeekType.Even : WeekType.Odd;
        }

        view.setWeekType(weekType);
        view.setWeekdays(dayOfWeek);
        view.setStartDate(startDate);
        view.setEndDate(endDate);
    }

    @Autowired
    public void setScheduleService(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @Autowired
    public void setDisciplineService(DisciplineService disciplineService) {
        this.disciplineService = disciplineService;
    }

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Autowired
    public void setTimeService(TimeService timeService) {
        this.timeService = timeService;
    }

    @Autowired
    public void setTypeService(TypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTeachersRepository(TeachersRepository teachersRepository) {
        this.teachersRepository = teachersRepository;
    }

    @Autowired
    public void setTdtRepository(TdtRepository tdtRepository) {
        this.tdtRepository = tdtRepository;
    }

    @Autowired
    public void setLessonRepository(LessonRepository lessonRepository) {
        this.lessonRepository = lessonRepository;
    }

    @Autowired
    public void setLessonDateRepository(LessonDateRepository lessonDateRepository) {
        this.lessonDateRepository = lessonDateRepository;
    }
}
