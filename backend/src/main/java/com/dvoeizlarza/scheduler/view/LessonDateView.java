package com.dvoeizlarza.scheduler.view;

import com.dvoeizlarza.scheduler.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LessonDateView {
    private Long id;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String discipline;
    private String type;
    private List<String> teachers;
    private String auditorium;
    private List<NoteType> modifiedField;
    private String comment;
}
