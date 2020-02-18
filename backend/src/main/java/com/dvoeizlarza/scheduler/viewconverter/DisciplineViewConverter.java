package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.Discipline;
import com.dvoeizlarza.scheduler.entity.TDT;
import com.dvoeizlarza.scheduler.view.DisciplineView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class DisciplineViewConverter implements Converter<Discipline, DisciplineView> {
    @Override
    public DisciplineView convert(Discipline discipline) {
        DisciplineView view = new DisciplineView();
        view.setId(discipline.getId());
        view.setName(discipline.getName());
        view.setShortName(discipline.getShortName());
        view.setCertificationType(discipline.getCertificationType());
        view.setSchedule(discipline.getSchedule().getId());
        if(discipline.getTdts()!=null) {
            view.setTdts(discipline.getTdts().stream().map(TDT::getId).collect(Collectors.toList()));
        }
        return view;
    }
}
