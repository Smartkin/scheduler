package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.Lesson;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.view.TimeView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TimeViewConverter implements Converter<Time, TimeView> {
    @Override
    public TimeView convert(Time time) {
        TimeView view = new TimeView();
        view.setSchedule(time.getSchedule().getId());
        view.setId(time.getId());
        view.setName(time.getName());
        view.setBegin(time.getBegin());
        view.setEnd(time.getEnd());
        if(time.getLessons()!=null) {
            view.setLessons(time.getLessons().stream().map(Lesson::getId).collect(Collectors.toList()));
        }
        return view;
    }
}
