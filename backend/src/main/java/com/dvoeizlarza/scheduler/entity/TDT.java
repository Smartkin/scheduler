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
public class TDT {
    // Идентификатор
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Внешние ключи
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCIPLINE_ID")
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TYPE_ID")
    private Type type;

    // Атомарные атрибуты
    //-----

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tdt")
    private List<Teachers> teachers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "tdt")
    private List<Lesson> lessons;
}
