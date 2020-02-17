package com.dvoeizlarza.scheduler.entity;

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
public class Schedule {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    //-----

    // Атомарные атрибуты
    private String university;
    private String faculty;
    private String groupName;
    private String semester;
    private LocalDate start;
    private LocalDate stop;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Teacher> teachers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Discipline> disciplines;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Type> types;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "schedule")
    private List<Time> times;
}
