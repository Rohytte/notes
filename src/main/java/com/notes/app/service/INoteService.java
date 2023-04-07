package com.notes.app.service;

import com.notes.app.entity.Note;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface INoteService {

    public List<Note> getAllNotes();

    public Note getNoteById(String id);

    public Note addNote(Note note);

    public void deleteNoteById(String id);

}
