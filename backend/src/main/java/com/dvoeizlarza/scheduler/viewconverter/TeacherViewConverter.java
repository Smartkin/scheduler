package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.Teacher;
import com.dvoeizlarza.scheduler.entity.Teachers;
import com.dvoeizlarza.scheduler.view.TeacherView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeacherViewConverter implements Converter<Teacher, TeacherView> {
    @Override
    public TeacherView convert(Teacher teacher) {
        TeacherView view = new TeacherView();
        view.setId(teacher.getId());
        view.setInfo(teacher.getInfo());
        view.setName(teacher.getName());
        view.setSchedule(teacher.getSchedule().getId());
        view.setTeachers(teacher.getTeachers().stream().map(Teachers::getId).collect(Collectors.toList()));
        return view;
    }
}
