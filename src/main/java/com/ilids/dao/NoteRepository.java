package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Notes;

@Component
public class NoteRepository extends AbstractGenericDao<Notes> {

    public NoteRepository() {
        super(Notes.class);
    }

}
