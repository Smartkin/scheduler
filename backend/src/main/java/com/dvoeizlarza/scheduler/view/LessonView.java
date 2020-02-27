package com.dvoeizlarza.scheduler.view;

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
public class LessonView {
    private WeekType weekType;
    private DayOfWeek weekdays;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<LocalDate> dates;

    private Long schId;
    private List<Long> teacherList;
    private Long discipline;
    private Long type;
    private Long time;

    private String auditory;
    private String comment;
}