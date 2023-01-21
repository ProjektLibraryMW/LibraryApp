package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;

public class BookForm extends FormLayout {
    TextField title = new TextField("Tytuł");
    TextField author = new TextField("Tytuł");
    TextField published = new TextField("Data wydania");

    public BookForm(List<Book> book) {
        addClassName("book-form");
    }


}
