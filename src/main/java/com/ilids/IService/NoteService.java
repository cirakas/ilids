/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.Notes;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface NoteService {

    /**
     *
     * @return
     */
    public List<Notes> getAllNotes();

    /**
     *
     * @param id
     * @return
     */
    public Notes findById(Long id);

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Notes remove(Long id) throws Exception;

    /**
     *
     * @param notes
     * @return
     * @throws Exception
     */
    public boolean addNote(Notes notes) throws Exception;

    /**
     *
     * @param name
     * @return
     */
    public Notes createNote(String name);
}
