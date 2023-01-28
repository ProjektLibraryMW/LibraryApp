package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.repository.UserRepository;
import com.example.application.backend.service.BookService;
import com.example.application.backend.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import static org.junit.jupiter.api.Assertions.*;

public class AdminViewTest {


    @Mock
    private BookRepository bookRepository;

    DefaultCrudFormFactory factory = new DefaultCrudFormFactory(Book.class);


    @InjectMocks
    private BookService bookService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void AdminViewTest() {
        AdminView admin = new AdminView(bookService);
        var crud = new GridCrud<>(Book.class, new HorizontalSplitCrudLayout(), factory );
    }

}