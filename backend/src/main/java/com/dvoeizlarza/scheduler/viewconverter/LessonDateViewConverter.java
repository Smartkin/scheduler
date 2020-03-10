package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.LessonDate;
import com.dvoeizlarza.scheduler.entity.Note;
import com.dvoeizlarza.scheduler.entity.TDT;
import com.dvoeizlarza.scheduler.entity.Time;
import com.dvoeizlarza.scheduler.enums.NoteType;
import com.dvoeizlarza.scheduler.view.LessonDateView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LessonDateViewConverter implements Converter<LessonDate, LessonDateView> {
    @Override
    public LessonDateView convert(LessonDate lessonDate) {
        TDT tdt = lessonDate.getLesson().getTdt();
        Time time = lessonDate.getLesson().getTime();
        LessonDateView view = new LessonDateView();
        view.setId(lessonDate.getId());
        view.setLesson(lessonDate.getLesson().getId());
        view.setDate(lessonDate.getDate());
        view.setDiscipline(tdt.getDiscipline().getName());
        view.setStart(time.getBegin());
        view.setEnd(time.getEnd());
        view.setTeachers(tdt.getTeachers().stream().map(x->x.getTeacher().getName()).collect(Collectors.toList()));
        view.setType(tdt.getType().getName());
        view.setAuditorium(lessonDate.getLesson().getAuditorium());
        if(!lessonDate.getNotes().isEmpty()) {
            List<NoteType> modifiedFields = new LinkedList<>();
            StringBuilder comment = new StringBuilder();
            for(Note n: lessonDate.getNotes()){
                modifiedFields.add(n.getNoteType());
                switch (n.getNoteType()){
                    case EndTime:
                        view.setEnd(LocalTime.parse(n.getValue()));
                    break;
                    case Teacher:
                        List<String> teachers = new LinkedList<>();
                        String[] ts = n.getValue().split(",");
                        for(String s: ts){
                            teachers.add(s.trim());
                        }
                        view.setTeachers(teachers);
                    break;
                    case StartTime:
                        view.setStart(LocalTime.parse(n.getValue()));
                    break;
                    case Auditorium:
                        view.setAuditorium(n.getValue());
                    break;
                    case Discipline:
                        view.setDiscipline(n.getValue());
                    break;
                    case LessonType:
                        view.setType(n.getValue());
                    break;
                }
                comment.append(n.getText()).append(" {").append(n.getNoteType()).append(" = '").append(n.getValue()).append("'}\n");
            }
            view.setModifiedField(modifiedFields);
            view.setComment(comment.toString().trim());
        }
        return view;
    }
}
