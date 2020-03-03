package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
