package com.ilids.repository.impl;

import com.ilids.IRepository.NoteRepository;
import org.springframework.stereotype.Component;

import com.ilids.domain.Notes;

/**
 *
 * @author cirakas
 */
@Component
public class NoteRepositoryImpl extends GenericRepositoryImpl<Notes> implements NoteRepository{

    /**
     *
     */
    public NoteRepositoryImpl() {
        super(Notes.class);
    }

}
