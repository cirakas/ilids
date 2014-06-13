package com.ilids.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.Notes;
import com.ilids.service.NoteService;
import java.util.List;

@Controller
public class NoteController {

   
    @Autowired
    private NoteService noteService;
    

   @ModelAttribute("notesList")
    public List<Notes> getNotes() {
        return noteService.getAllNotes();
    }
  @ModelAttribute("noteAdd")
    public Notes getNote() {
        return new Notes();
    }

    @RequestMapping(value = "/note/add", method = RequestMethod.GET)
    public String show() {
        System.out.println("Inside the show");
        return "/note/add";
    }

    @RequestMapping(value = "/note/create", method = RequestMethod.POST)
    public String add(Notes note, RedirectAttributes flash) {
            if (noteService.addNote(note))
                flash.addFlashAttribute("success", "Note has been successfully added.");
            else
                flash.addFlashAttribute("error", "User cannot be created.");
            
        return "redirect:/note/add";
    }

    @RequestMapping(value = "/note/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("noteid") Long noteId) {
        noteService.remove(noteId);
        return "redirect:/note/add";
    }

}
