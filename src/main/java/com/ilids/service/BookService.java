package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.BookRepository;
import com.ilids.domain.Book;
import com.ilids.domain.User;

@Component
@Transactional
public class BookService {

    @Autowired
    private UserService userService;
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAll();
    }

    public Book findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book remove(Long id) {
        Book book = bookRepository.findById(id);
        if (book == null) {
            throw new IllegalArgumentException();
        }
        book.getUser().getBooks().remove(book); //pre remove
        bookRepository.delete(book);
        return book;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public boolean addBookToUser(String bookname, String username) {
        Book book = createBook(bookname);
        User user = userService.findByCustomField("username", username);
        if (user == null) {
            throw new IllegalArgumentException();
        }
        user.addBook(book);
        userService.persist(user);
        return true;
    }

    private Book createBook(String name) {
        return new Book(name);
    }

}
