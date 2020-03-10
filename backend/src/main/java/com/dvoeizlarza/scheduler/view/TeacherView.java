package com.dvoeizlarza.scheduler.view;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.Teachers;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Comparator;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherView {
    private Long id;
    private Long schedule;
    private String name;
    private String info;
    private List<Long> teachers;

    public static final Comparator<TeacherView> COMPARE_BY_NAME = Comparator.comparing(TeacherView::getName);
}
