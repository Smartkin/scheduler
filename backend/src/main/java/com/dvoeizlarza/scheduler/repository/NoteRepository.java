package com.dvoeizlarza.scheduler.repository;

import com.dvoeizlarza.scheduler.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
