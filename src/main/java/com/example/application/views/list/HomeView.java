package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.time.LocalDate;

@PageTitle("Biblioteka")
@Route("")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    Grid<Book> grid = new Grid<>(Book.class);
    TextField filterText = new TextField();

    BookService service;
    public HomeView(BookService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        configureGrid();

        add(new H1("Książki do wypożyczenia"),grid
        );
        updateList();
    }


    private void updateList() {
        grid.setItems(service.findAll());
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        grid.addColumn(book -> book.getTitle()).setHeader("Tytuł");
        grid.addColumn(book -> book.getAuthor()).setHeader("Autor");
        grid.addColumn(book -> book.getPublished()).setHeader("Data publikacji");
        grid.addColumn(book -> book.getPages()).setHeader("Ilość stron");
        grid.addColumn(createStatusComponentRenderer()).setHeader("Dostępność")
                .setAutoWidth(true);
        grid.addComponentColumn(book -> {Button editButton = new Button("Wypożycz");
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(e -> { Wypozycz(book, service);
            });
            return editButton;
        });
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    protected void Wypozycz(Book book, BookService service){

        if(book.getNumberOf()<0) {
        }else {
            book.setNumberOf(book.getNumberOf() - 1);
            service.update(book);
            System.out.println(book.getNumberOf());
            updateList();
        }


    }

    private static final SerializableBiConsumer<Span, Book> statusComponentUpdater = (
            span, book)-> {
        boolean isAvailable = false;
        if(book.getNumberOf()>0){
            isAvailable=true;
        }
        String theme= String.format("badge %s", isAvailable? "success primary" : "error primary");
        span.getElement().setAttribute("theme", theme);
        span.setText(isAvailableBook(book));

    };

    protected static String isAvailableBook(Book book)
    {
        String text = "";
        if(book.getNumberOf()>0){
            text="Dostępna";

        }
        else {
            text="Niedostępna";
        }
        return text;
    }

    private static ComponentRenderer<Span, Book> createStatusComponentRenderer() {
        return new ComponentRenderer<>(Span::new, statusComponentUpdater);
    }

}
