package com.example.application.backend.service;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;


    @InjectMocks
    private BookService bookService;



    @Test
    public void SavedShouldBeSaved() {

        Book book = new Book(1, "t", "t", LocalDate.now(), 123, 1);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.add(book);
        System.out.println(bookRepository.findById(0L));

    }

    @Test
    public void DeletedShouldBeDeleted() {

        Book book = new Book(1, "t", "t", LocalDate.now(), 123, 1);
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        bookService.add(book);
        System.out.println(bookRepository.findById(0L));
        bookService.delete(book);
        System.out.println(bookService.findAll());
    }
}