package com.ilids.controller;

import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.Notes;
import com.ilids.domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 *
 * @author cirakas
 */
@Controller
public class NoteController {

    @Autowired
    private NoteService noteService;
    
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    
    @Autowired
    ExceptionLogService exceptionLogService;

    String module = "";

    /**
     *
     * @return
     */
    @ModelAttribute("notesList")
    public List<Notes> getNotes() {
	return noteService.getAllNotes();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("noteAdd")
    public Notes getNote() {
	return new Notes();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/note/add", method = RequestMethod.GET)
    public String show() {
	getModuleName();
	return "/note/add";
    }

    /**
     *
     */
    public void getModuleName() {
	Properties prop = new Properties();
	String propFileName = "messages_en.properties";
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	try {
	    prop.load(inputStream);
	    module = prop.getProperty("label.addNote");
	} catch (IOException ex1) {

	}
    }

    /**
     *
     * @param note
     * @param flash
     * @return
     */
    @RequestMapping(value = "/note/create", method = RequestMethod.POST)
    public String add(Notes note, RedirectAttributes flash) {
	boolean status = false;
	try {
	    status = noteService.addNote(note);
	    if (status) {
		flash.addFlashAttribute("success", "Note has been successfully added.");
	    } else {
		flash.addFlashAttribute("error", "User cannot be created.");
	    }
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Note creation is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'add' ]");
	}

	return "redirect:/note/add";
    }

    /**
     *
     * @param noteId
     * @return
     */
    @RequestMapping(value = "/note/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("noteid") Long noteId) {
	try {
	    noteService.remove(noteId);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Note deletion is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'delete' ]");
	}
	return "redirect:/note/add";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/note/home", method = RequestMethod.GET)
    public String showHome() {
	return "redirect:/home";
    }

}
