package com.example.application.backend.entity;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    Book book1;


    @Test
    public void NoArgsConstructorShouldCreateBookWithNullFields() {
        book1 = new Book();
        assertNull(book1.getTitle());
        assertNull(book1.getAuthor());
        assertNull(book1.getPublished());
        assertNull(book1.getPages());
        assertNull(book1.getNumberOf());

    }

    @Test
    public void CreateBook() {
        book1 = new Book(1, "test", "testauthor", LocalDate.now(), 123, 1);
        assertEquals(1, book1.getId());
        assertEquals("test", book1.getTitle());
        assertEquals("testauthor", book1.getAuthor());
        assertEquals(LocalDate.now(), book1.getPublished());
        assertEquals(123, book1.getPages());
        assertEquals(1, book1.getNumberOf());
    }

    @Test
    public void SettingNewAttributesShouldChangeThem() {
        book1 = new Book(1, "test", "testauthor", LocalDate.now(), 123, 1);
        book1.setId(2);
        book1.setTitle("nowytytul");
        assertEquals(2, book1.getId());
        assertEquals("nowytytul", book1.getTitle());
    }
    @Test
    public void NegativeNumberShouldDefaultTo0() {
        book1 = new Book(1, "test", "testauthor", LocalDate.now(), 123, -2);
        book1.setNumberOf(-2);
        assertEquals(0, book1.getNumberOf());
    }

    @Test
    public void PositiveNumberShouldNotBeChanged() {
        book1 = new Book(1, "test", "testauthor", LocalDate.now(), 123, 1);
        assertEquals(1, book1.getNumberOf());
    }
    @Test
    public void PagesShouldReturnPageNumber() {

    }

}