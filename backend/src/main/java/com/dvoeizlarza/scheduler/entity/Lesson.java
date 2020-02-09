package com.dvoeizlarza.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Lesson {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TDT_ID")
    private TDT tdt;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TIME_ID")
    private Time time;

    // Атомарные атрибуты
    public String auditorium;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "lesson")
    private List<Meeting> meetings;
}
