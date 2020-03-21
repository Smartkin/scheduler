package com.dvoeizlarza.scheduler.service;

import com.dvoeizlarza.scheduler.dto.NoteDto;
import com.dvoeizlarza.scheduler.entity.LessonDate;
import com.dvoeizlarza.scheduler.entity.Note;
import com.dvoeizlarza.scheduler.enums.LessonDateStatus;
import com.dvoeizlarza.scheduler.enums.NoteType;
import com.dvoeizlarza.scheduler.repository.LessonDateRepository;
import com.dvoeizlarza.scheduler.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LessonDateService {
    private LessonDateRepository lessonDateRepository;
    private NoteRepository noteRepository;

    public LessonDate read(Long id) {
        return lessonDateRepository.findById(id).orElse(null);
    }

    public List<LessonDate> readList(Long schId, LocalDate date, int count) {
        return lessonDateRepository.findBySchIdBetweenDates(schId, date, date.plusDays(count));
    }

    public Note modify(NoteDto dto) {
        LessonDate lessonDate = read(dto.getLessonDate());
        Note note = new Note();
        note.setLessonDate(lessonDate);
        note.setNoteType(dto.getNoteType());
        note.setText(dto.getText());
        note.setValue(dto.getValue());
        noteRepository.save(note);
        return note;
    }

    public LessonDate delete(Long id) {
        LessonDate lessonDate = read(id);
        LessonDateStatus status = lessonDate.getLessonDateStatus();
        if(status.equals(LessonDateStatus.Cancelled)){
            lessonDate.setLessonDateStatus(LessonDateStatus.Planned);
        }else {
            lessonDate.setLessonDateStatus(LessonDateStatus.Cancelled);
        }
        return lessonDate;
    }

    @Autowired
    public void setNoteRepository(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Autowired
    public void setLessonDateRepository(LessonDateRepository lessonDateRepository) {
        this.lessonDateRepository = lessonDateRepository;
    }
}
