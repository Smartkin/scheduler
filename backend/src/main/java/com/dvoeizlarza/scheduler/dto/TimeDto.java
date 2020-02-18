package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.entity.Schedule;
import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeDto {
    private Long id;

    private Long schId;

    private String name;
    private LocalTime begin;
    private LocalTime end;
}
