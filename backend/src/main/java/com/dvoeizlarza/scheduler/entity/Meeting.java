package com.dvoeizlarza.scheduler.entity;

import com.dvoeizlarza.scheduler.enums.MeetingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Meeting {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;

    // Атомарные атрибуты
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private MeetingStatus meetingStatus;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "meeting")
    private List<Note> notes;
}
