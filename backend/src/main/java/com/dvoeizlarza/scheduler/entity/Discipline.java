package com.dvoeizlarza.scheduler.entity;

import com.dvoeizlarza.scheduler.enums.CertificationType;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Discipline {
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
    private String shortName;
    @Enumerated(EnumType.STRING)
    private CertificationType certificationType;

    // Зависимости
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discipline")
    private List<TDT> tdts;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "discipline")
    private List<Homework> homework;
}
