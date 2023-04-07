package com.notes.app.controller;

import com.notes.app.entity.Note;
import com.notes.app.service.NoteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Controller
public class NoteController {

    @Autowired
    private NoteServiceImpl service;

    @GetMapping("/")
    public String index() {
        return "createnote";
    }

    @GetMapping("/note/{id}")
    public String getNoteById(@PathVariable String id, Model model) {
        Note note = service.getNoteById(id);
        if (note == null) {
            return "notfound";
        }
            model.addAttribute("note", note);
            if(note.getExpiration().equals("1H")){
                note.setExpiration("1 Hour");
            }
            if(note.getExpiration().equals("1D")){
                note.setExpiration("1 Day");
            }
            if(note.getExpiration().equals("1W")){
                note.setExpiration("1 Week");
            }
            if(note.getExpiration().equals("1M")){
                note.setExpiration("1 Month");
            }
            if(note.getExpiration().equals("6M")){
                note.setExpiration("6 Months");
            }
            if(note.getExpiration().equals("NE")){
                note.setExpiration("Never Burn");
            }
            return "note";
    }

    @GetMapping("/note/create")
    public String showCreateNoteForm(Note note) {
        return "createnote";
    }

    @PostMapping("/note/create")
    public String createNote(@Valid Note note, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "createnote";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Instant instant = date.toInstant();
        LocalDateTime formattedDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        note.setCreationDate(formattedDate);
        service.addNote(note);
        return "redirect:/note/"+note.getId();
    }

}
