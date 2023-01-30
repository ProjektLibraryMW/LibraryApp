package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.service.BookService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static com.example.application.views.list.HomeView.isAvailableBook;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HomeViewTest {


    Book book1;
    Book book2;
    Book book3;

    @Mock
    BookRepository bookRepository;
    @InjectMocks
    BookService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void HomeTest() {
        HomeView admin = new HomeView(service);
        assertNotNull(admin.grid.getColumns());
    }

    @Test
    public void isFormPopulated() {
        book1 = new Book(1,"test","testauthho", LocalDate.now(),1,1);
        book2 = new Book(1,"test","test", LocalDate.now(),1,0);
    }

    @Test
    public void testIsAvailableBook() {

        book1 = new Book(1,"test","testauthho", LocalDate.now(),1,1);
        book2 = new Book(1,"test","test", LocalDate.now(),1,0);
        assertEquals("Dostępna: 1 szt.",isAvailableBook(book1));
        assertEquals("Niedostępna",isAvailableBook(book2));
    }


    @Test
    public void testWypozycz()
    {

            book3 = new Book(1,"test","test", LocalDate.now(),1,1);

        assertTrue(book3.getNumberOf() > 0);


    }

    @Test
    public  void teststatusComponentUpdater() {
        book1 = new Book(1, "test", "test", LocalDate.now(), 1, 1);
        assertEquals("Dostępna: 1 szt.", isAvailableBook(book1));
    }

}