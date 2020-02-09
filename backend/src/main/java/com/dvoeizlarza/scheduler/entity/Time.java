package com.dvoeizlarza.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Time {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;

    // Атомарные атрибуты
    private String name;
    private LocalTime begin;
    private LocalTime end;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "time")
    private List<Lesson> lessons;
}
