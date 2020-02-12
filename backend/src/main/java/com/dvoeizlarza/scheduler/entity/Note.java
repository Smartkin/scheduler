package com.dvoeizlarza.scheduler.entity;

import com.dvoeizlarza.scheduler.enums.NoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.lang.model.type.NoType;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Note {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSONDATE_ID")
    private LessonDate lessonDate;

    // Атомарные атрибуты
    private NoteType noteType;
    private String value;
    private String text;


    // Зависимости
    //-----
}
