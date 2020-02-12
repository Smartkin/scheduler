package com.dvoeizlarza.scheduler.view;

import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.enums.WeekType;
import com.dvoeizlarza.scheduler.enums.Weekdays;
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
public class LessonViewConstruct {
    private WeekType weekType;
    private Weekdays weekdays;
    private List<LocalDate> dates;
    private Long type;
    private Long time;
    private List<Long> teachers;

}
