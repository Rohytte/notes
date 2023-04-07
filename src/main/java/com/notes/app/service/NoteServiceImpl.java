package com.notes.app.service;

import com.notes.app.entity.Note;
import com.notes.app.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements INoteService {

    @Autowired
    private NoteRepository repository;

    public List<Note> getAllNotes() {
        return repository.findAll();
    }

    public Note getNoteById(String id) {
        return repository.findById(id).orElse(null);
    }

    public Note addNote(Note note) {
        return repository.save(note);
    }

    public void deleteNoteById(String id) {
        repository.deleteById(id);
    }
}
