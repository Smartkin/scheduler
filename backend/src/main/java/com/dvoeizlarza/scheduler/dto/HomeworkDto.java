package com.dvoeizlarza.scheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class HomeworkDto {
    private Long id;
    private Long discipline;
    private String text;
    private LocalDate date;
    private List<MultipartFile> files;
}
