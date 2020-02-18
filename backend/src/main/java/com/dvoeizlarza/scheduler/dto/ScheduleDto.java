package com.dvoeizlarza.scheduler.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScheduleDto {
    private Long id;

    private String university;
    private String faculty;
    private String groupName;
    private String semester;
    private LocalDate start;
    private LocalDate stop;
}
