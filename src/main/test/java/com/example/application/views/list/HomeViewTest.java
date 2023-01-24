package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static com.example.application.views.list.HomeView.isAvailableBook;
import static org.junit.Assert.*;

public class HomeViewTest {


    Book book1;
    Book book2;

    @BeforeEach
    public void setupData() {

        //book = new Book(1,"test","testauthho", LocalDate.now(),1,1);
    }

    @Test
    public void isAvailableBook_test() {

        book1 = new Book(1,"test","testauthho", LocalDate.now(),1,1);
        book2 = new Book(1,"test","test", LocalDate.now(),1,0);
        assertEquals("Dostępna",isAvailableBook(book1));
        assertEquals("Niedostępna",isAvailableBook(book2));
    }


}