package com.ilids.service.impl;

import com.ilids.IRepository.NoteRepository;
import com.ilids.IService.NoteService;
import com.ilids.IService.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.repository.impl.NoteRepositoryImpl;
import com.ilids.domain.Notes;
import com.ilids.domain.User;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class NoteServiceImpl implements NoteService {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteRepository noteRepository;

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @Override
    public List<Notes> getAllNotes() {
	return noteRepository.getAll();
    }

    @Override
    public Notes findById(Long id) {
	return noteRepository.findById(id);
    }

    @Override
    public Notes remove(Long id) throws Exception {
	Notes notes = noteRepository.findById(id);
	if (notes == null) {
	    throw new IllegalArgumentException();
	}
	//note.getUser().getNotes().remove(note); //pre remove
	noteRepository.delete(notes);
	return notes;
    }

    @Override
    public boolean addNote(Notes notes) throws Exception {
	notes.setCreatedDate(new Date());
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	String userName = auth.getName();
	User user = userService.findByCustomField("username", userName);
	notes.setUser(user);
	noteRepository.persist(notes);
	return true;
    }

    @Override
    public Notes createNote(String name) {
	return new Notes(name);
    }

}
