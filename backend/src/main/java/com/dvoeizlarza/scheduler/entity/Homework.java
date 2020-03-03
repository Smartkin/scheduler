package com.dvoeizlarza.scheduler.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DISCIPLINE_ID")
    private Discipline discipline;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USR_ID")
    private USR usr;

    private String text;
    private LocalDate date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "homework")
    private List<File> files;
}
