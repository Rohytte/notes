package com.notes.app.repository;

import com.notes.app.entity.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {

    List<Note> findByExpirationBefore(LocalDate date);

}
