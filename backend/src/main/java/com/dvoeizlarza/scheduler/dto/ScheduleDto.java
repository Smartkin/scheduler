package com.dvoeizlarza.scheduler.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleDto {
    private Long id;

    private String university;
    private String faculty;
    private String groupName;
    private String semester;
    private LocalDate start;
    private LocalDate stop;
}
