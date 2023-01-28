package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.service.BookService;
import com.example.application.views.list.HomeView;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;
import java.util.Optional;

import static com.example.application.views.list.HomeView.isAvailableBook;
import static org.junit.Assert.*;

public class HomeViewTest {


    Book book1;
    Book book2;
    Book book3;
    HomeView homeView;
    BookService service;

    @Test
    public void isFormPopulated() {
        book1 = new Book(1,"test","testauthho", LocalDate.now(),1,1);
        book2 = new Book(1,"test","test", LocalDate.now(),1,0);
    }

    @Test
    public void testIsAvailableBook() {

        book1 = new Book(1,"test","testauthho", LocalDate.now(),1,1);
        book2 = new Book(1,"test","test", LocalDate.now(),1,0);
        assertEquals("Dostępna",isAvailableBook(book1));
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
        assertEquals("Dostępna", isAvailableBook(book1));
    }

}