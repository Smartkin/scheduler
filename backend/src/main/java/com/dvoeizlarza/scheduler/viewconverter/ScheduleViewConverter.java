package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.*;
import com.dvoeizlarza.scheduler.view.ScheduleView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ScheduleViewConverter implements Converter<Schedule, ScheduleView> {
    @Override
    public ScheduleView convert(Schedule source) {
        ScheduleView view = new ScheduleView();
        view.setId(source.getId());
        view.setUniversity(source.getUniversity());
        view.setFaculty(source.getFaculty());
        view.setGroupName(source.getGroupName());
        view.setSemester(source.getSemester());
        view.setDisciplines(source.getDisciplines().stream().map(Discipline::getId).collect(Collectors.toList()));
        view.setTeachers(source.getTeachers().stream().map(Teacher::getId).collect(Collectors.toList()));
        view.setTypes(source.getTypes().stream().map(Type::getId).collect(Collectors.toList()));
        view.setTimes(source.getTimes().stream().map(Time::getId).collect(Collectors.toList()));
        return view;
    }
}
