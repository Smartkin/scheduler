package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.entity.Schedule;
import lombok.*;

import java.time.LocalTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TimeDto {
    private Long id;

    private Long schId;

    private String name;
    private LocalTime begin;
    private LocalTime end;
}
