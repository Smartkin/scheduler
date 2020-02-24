package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NoteDto {
    private Long id;
    private Long lessonDate;
    private NoteType noteType;
    private String value;
    private String text;
}