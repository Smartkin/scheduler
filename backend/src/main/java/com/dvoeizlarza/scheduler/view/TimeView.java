package com.dvoeizlarza.scheduler.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Comparator;
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

    public static final Comparator<TimeView> COMPARE_BY_START = (timeView, t1) -> {
        if(timeView.getBegin().equals(t1.getBegin())){
            return 0;
        }
        return timeView.getBegin().isAfter(t1.getBegin())?1:-1;
    };
}
