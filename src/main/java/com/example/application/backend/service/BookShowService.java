package com.example.application.backend.service;


import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookShowService {

    private final BookRepository bookRepository;


    public BookShowService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAllBooks(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return bookRepository.findAll();
        } else {
            return bookRepository.search(stringFilter);
        }
    }

    public long countBooks() {
        return bookRepository.count();
    }

}
