package com.dvoeizlarza.scheduler.view;

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
public class TypeView {
    private Long id;
    private Long schedule;
    private String name;
    private List<Long> tdts;

    public static final Comparator<TypeView> COMPARE_BY_NAME = Comparator.comparing(TypeView::getName);
}
