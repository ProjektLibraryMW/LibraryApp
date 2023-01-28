package com.example.application.backend.service;

import com.example.application.backend.repository.BookRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {

    @Mock
    private BookRepository bookRepository;


    @InjectMocks
    private BookService bookService;


}