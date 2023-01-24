package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.DataProviderListener;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LitRenderer;
import com.vaadin.flow.data.renderer.Renderer;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

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

    private static Renderer<Book> createBookRenderer() {
        return LitRenderer.<Book> of(
                        "<vaadin-horizontal-layout style=\"align-items: center;\" theme=\"spacing\">"
                                + "<vaadin-avatar img=\"${item.pictureUrl}\" name=\"${item.title}\" alt=\"User avatar\"></vaadin-avatar>"
                                + "  <vaadin-vertical-layout style=\"line-height: var(--lumo-line-height-m);\">"
                                + "    <span> ${item.title} </span>"
                                + "    <span style=\"font-size: var(--lumo-font-size-s); color: var(--lumo-secondary-text-color);\">"
                                + "      ${item.author}" + "    </span>"
                                + "  </vaadin-vertical-layout>"
                                + "</vaadin-horizontal-layout>")
                .withProperty("title",book -> book.getTitle())
                .withProperty("author", book -> book.getAuthor());
    }

    private void configureForm() {
    }

    private void updateList() {
        grid.setItems(service.findAll());
    }
    private void configureGrid() {
        Button button = new Button("click me");
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
            editButton.addClickListener(e -> { Wypozycz(book, service);
            });
            return editButton;
        });
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void Wypozycz(Book book, BookService service){

        if(book.getNumberOf()>0) {
            book.setNumberOf(book.getNumberOf() - 1);
            service.update(book);
            System.out.println(book.getNumberOf());
        }
        updateList();
    }

    private static final SerializableBiConsumer<Span, Book> statusComponentUpdater = (
            span, book)-> {
        boolean isAvailable = false;
        if(book.getNumberOf()>0){
            isAvailable=true;
        }
        String theme= String.format("badge %s", isAvailable? "success" : "error");
        span.getElement().setAttribute("theme", theme);
        span.setText(isAvailableBook(book));

    };

    private static String isAvailableBook(Book book)
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
