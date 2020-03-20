package com.dvoeizlarza.scheduler.view;

import com.dvoeizlarza.scheduler.enums.LessonDateStatus;
import com.dvoeizlarza.scheduler.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDateView {
    private Long id;
    private Long lesson;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private LessonDateStatus status;
    private String discipline;
    private String type;
    private List<String> teachers;
    private String auditorium;
    private List<NoteType> modifiedField;
    private String comment;

    public static final Comparator<LessonDateView> COMPARE_BY_START = (lessonDateView, t1) -> {
        if(lessonDateView.getDate().equals(t1.getDate()))
        {
            if(lessonDateView.getStart().equals(t1.getStart())){
                if(lessonDateView.getEnd().equals(t1.getEnd())){
                    return 0;
                }
                else {
                    return lessonDateView.getEnd().isAfter(t1.getEnd()) ? 1 : -1;
                }
            }else {
                return lessonDateView.getStart().isAfter(t1.getStart()) ? 1 : -1;
            }
        }
        else {
            return lessonDateView.getDate().isAfter(t1.getDate())?1:-1;
        }
    };
}
