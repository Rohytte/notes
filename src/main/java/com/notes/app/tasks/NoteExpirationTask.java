package com.notes.app.tasks;

import com.notes.app.entity.Note;
import com.notes.app.repository.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@EnableScheduling
public class NoteExpirationTask {

    private static final Logger LOG = LoggerFactory.getLogger(NoteExpirationTask.class);

    @Autowired
    NoteRepository noteRepository;

    public NoteExpirationTask(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Scheduled(cron = "0 * * * * *")
    public void deleteBurnedNotes() {
        LOG.info("Expiration task triggered at - {}", LocalDateTime.now());
        List<Note> notes = noteRepository.findAll();
        LOG.info("Total notes present - {}",notes.size());
        List<Note> burnedNotes = getBurnedNotes(notes);
        LOG.info("Total burned notes - {}",burnedNotes.size());
        for (Note note : burnedNotes) {
            noteRepository.delete(note);
        }
        LOG.info("Burned notes deleted");
    }

    private List<Note> getBurnedNotes(List<Note> notes){
        List<Note> burnedNotes = new ArrayList<>();
        for(Note note: notes){
            if(note.getExpiration().equals("NE")){
                continue;
            }
            if(note.getExpiration().equals("1H")){
                LocalDateTime creationTime = note.getCreationDate();
                LocalDateTime expirationTime = creationTime.plusHours(1);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(expirationTime)) {
                    burnedNotes.add(note);
                }
            }
            if(note.getExpiration().equals("1D")){
                LocalDateTime creationTime = note.getCreationDate();
                LocalDateTime expirationTime = creationTime.plusDays(1);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(expirationTime)) {
                    burnedNotes.add(note);
                }
            }
            if(note.getExpiration().equals("1W")){
                LocalDateTime creationTime = note.getCreationDate();
                LocalDateTime expirationTime = creationTime.plusDays(7);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(expirationTime)) {
                    burnedNotes.add(note);
                }
            }
            if(note.getExpiration().equals("1M")){
                LocalDateTime creationTime = note.getCreationDate();
                LocalDateTime expirationTime = creationTime.plusMonths(1);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(expirationTime)) {
                    burnedNotes.add(note);
                }
            }
            if(note.getExpiration().equals("6M")){
                LocalDateTime creationTime = note.getCreationDate();
                LocalDateTime expirationTime = creationTime.plusMonths(6);
                LocalDateTime now = LocalDateTime.now();
                if (now.isAfter(expirationTime)) {
                    burnedNotes.add(note);
                }
            }
        }
        return burnedNotes;
    }

}
