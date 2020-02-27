package com.dvoeizlarza.scheduler.entity;

import com.dvoeizlarza.scheduler.enums.LessonDateStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class LessonDate {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    // Атомарные атрибуты
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private LessonDateStatus lessonDateStatus;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lessonDate")
    private List<Note> notes;

    public static final Comparator<LessonDate> COMPARE_BY_DATE = (lessonDate, t1) -> {
        if (lessonDate.getDate().equals(t1.getDate())) {
            return 0;
        }
        return (lessonDate.getDate().isAfter(t1.getDate())) ? 1 : -1;
    };
}
