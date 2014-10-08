package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.NoteRepository;
import com.ilids.domain.Notes;
import com.ilids.domain.User;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;

@Component
@Transactional
public class NoteService {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteRepository noteRepository;
    
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    public List<Notes> getAllNotes() {
        return noteRepository.getAll();
    }

    public Notes findById(Long id) {
        return noteRepository.findById(id);
    }

    public Notes remove(Long id)throws Exception {
        Notes notes = noteRepository.findById(id);
        if (notes == null) {
            throw new IllegalArgumentException();
        }
        //note.getUser().getNotes().remove(note); //pre remove
        noteRepository.delete(notes);
        return notes;
    }

    
//    public boolean addNoteToUser(String notename, String username) {
//        Notes note = createNote(notename);
//        User user = userService.findByCustomField("username", username);
//        if (user == null) {
//            throw new IllegalArgumentException();
//        }
//     //   user.addNote(note);
//        userService.persist(user);
//        return true;
//    }
    
    public boolean addNote(Notes notes)throws Exception{
        notes.setCreatedDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName=auth.getName();
        User user=userService.findByCustomField("username", userName);
        notes.setUser(user);
        noteRepository.persist(notes);
        return true;
    }
    
    private Notes createNote(String name) {
        return new Notes(name);
    }

}
