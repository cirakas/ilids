package com.ilids.controller;

import com.ilids.IService.UserService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.User;

/**
 *
 * @author cirakas
 */
@Controller
public class BookController {

    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @Autowired
    //private BookService bookService;

    @ModelAttribute("users")
    public List<User> getUsers() {
	return userService.getAllUsersExceptAdmin();
    }

//    @ModelAttribute("books")
//    public List<Book> getBooks() {
//	return bookService.getAllBooks();
//    }

    /**
     *
     * @return
     */
    
    @RequestMapping(value = "/book/add", method = RequestMethod.GET)
    public String show() {
	return "/book/add";
    }

    /**
     *
     * @param bookName
     * @param username
     * @param flash
     * @return
     */
    @RequestMapping(value = "/book/add", method = RequestMethod.POST)
    public String add(@RequestParam("name") String bookName, @RequestParam("username") String username, RedirectAttributes flash) {
//	if (bookService.addBookToUser(bookName, username)) {
//	    flash.addFlashAttribute("success", "Book successfully added.");
//	} else {
//	    flash.addFlashAttribute("error", "Could not add book.");
//	}
	return "redirect:/book/add";
    }

    /**
     *
     * @param bookId
     * @return
     */
    @RequestMapping(value = "/book/delete", method = RequestMethod.POST)
    public String delete(@RequestParam("bookid") Long bookId) {
//	bookService.remove(bookId);
	return "redirect:/book/add";
    }

}
