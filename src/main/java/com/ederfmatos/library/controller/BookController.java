package com.ederfmatos.library.controller;

import com.ederfmatos.library.bean.BookPersistBean;
import com.ederfmatos.library.model.Book;
import com.ederfmatos.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.ederfmatos.library.lib.LibraryMapper.getMapper;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookPersistBean create(@RequestBody BookPersistBean in) {
        Book entity = getMapper().map(in, Book.class);
        entity = service.save(entity);

        return getMapper().map(entity, BookPersistBean.class);
    }

}