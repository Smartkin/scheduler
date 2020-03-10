package com.dvoeizlarza.scheduler.view;

import com.dvoeizlarza.scheduler.entity.Schedule;
import com.dvoeizlarza.scheduler.entity.TDT;
import com.dvoeizlarza.scheduler.enums.CertificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Comparator;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineView {
    private Long id;
    private Long schedule;
    private String name;
    private String shortName;
    private CertificationType certificationType;
    private List<Long> tdts;

    public static final Comparator<DisciplineView> COMPARE_BY_NAME = (disciplineView, t1) -> {
        return disciplineView.getName().compareTo(t1.getName());
    };
}
