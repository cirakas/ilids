package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.Notes;
import com.ilids.domain.Notes;
import com.ilids.domain.User;
import com.ilids.service.NoteService;
import com.ilids.service.UserService;

@Controller
public class NoteController {

    @Autowired
    private UserService userService;
    @Autowired
    private NoteService noteService;

    @ModelAttribute("users")
    public List<User> getUsers() {
       return userService.getAllUsersExceptAdmin();
   }

    @ModelAttribute("notes")
    public List<Notes> getNotes() {
        return noteService.getAllNotes();
    }

    @RequestMapping(value = "/note/add", method = RequestMethod.GET)
    public String show() {
        return "/note/add";
    }

    @RequestMapping(value = "/note/add", method = RequestMethod.POST)
    public String add(@RequestParam("name") String noteName, @RequestParam("username") String username, RedirectAttributes flash) {
        if (noteService.addNoteToUser(noteName, username)) {
            flash.addFlashAttribute("success", "Note successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add note.");
        }
        return "redirect:/note/add";
    }

    @RequestMapping(value = "/note/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("noteid") Long noteId) {
        noteService.remove(noteId);
        return "redirect:/note/add";
    }

}
