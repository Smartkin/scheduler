package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.entity.Schedule;
import lombok.Data;

@Data
public class TeacherDto {
    private Long id;

    private Long schId;

    private String name;
    private String info;

}
