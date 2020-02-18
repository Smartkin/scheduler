package com.dvoeizlarza.scheduler.dto;

import com.dvoeizlarza.scheduler.enums.CertificationType;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DisciplineDto {
    private Long id;

    private Long schId;

    private String name;
    private String shortName;
    private CertificationType certificationType;
}
