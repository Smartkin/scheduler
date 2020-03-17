package com.dvoeizlarza.scheduler.viewconverter;

import com.dvoeizlarza.scheduler.entity.TDT;
import com.dvoeizlarza.scheduler.entity.Type;
import com.dvoeizlarza.scheduler.view.TypeView;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TypeViewConverter implements Converter<Type, TypeView> {
    @Override
    public TypeView convert(Type type) {
        TypeView view = new TypeView();
        view.setId(type.getId());
        view.setName(type.getName());
        view.setSchedule(type.getSchedule().getId());
        if(type.getTdts()!=null) {
            view.setTdts(type.getTdts().stream().map(TDT::getId).collect(Collectors.toList()));
        }
        return view;
    }
}
