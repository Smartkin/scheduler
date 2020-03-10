package com.dvoeizlarza.scheduler.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleView {
    private Long id;
    private String university;
    private String faculty;
    private String groupName;
    private String semester;
    private LocalDate start;
    private LocalDate stop;
    private List<Long> teachers;
    private List<Long> disciplines;
    private List<Long> types;
    private List<Long> times;
}
