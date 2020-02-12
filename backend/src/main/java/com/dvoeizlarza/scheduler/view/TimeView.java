package com.dvoeizlarza.scheduler.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeView {
    private Long id;
    private Long schedule;
    private String name;
    private LocalTime begin;
    private LocalTime end;
    private List<Long> lessons;
}
