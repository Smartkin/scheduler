package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.entity.*;
import com.dvoeizlarza.scheduler.enums.WeekType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {
    private WeekType weekType;
    private DayOfWeek weekdays;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> dates;

    private Long schId;
    private List<Teacher> teacherList;
    private Discipline discipline;
    private Type type;
    private Time time;

    private String auditory;
    private String comment;
}
