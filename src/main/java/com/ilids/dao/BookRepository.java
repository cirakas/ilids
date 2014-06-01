package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Book;

@Component
public class BookRepository extends AbstractGenericDao<Book> {

    public BookRepository() {
        super(Book.class);
    }

}
