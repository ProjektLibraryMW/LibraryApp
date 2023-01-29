package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import java.util.List;
import java.util.function.Consumer;

@PageTitle("Biblioteka")
@Route("")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    Grid<Book> grid = new Grid<>(Book.class);

    BookService service;
    public HomeView(BookService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        configureGrid();

        List<Book> people = (List<Book>) service.findAll();
        GridListDataView<Book> dataView = grid.setItems(people);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Wyszukaj po nazwie lub autorze");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        dataView.addFilter(book -> {
                    String searchTerm = searchField.getValue().trim();

                    if (searchTerm.isEmpty())
                        return true;

                    boolean matchesTitle = matchesTerm(book.getTitle(),
                            searchTerm);
                    boolean matchesAuthor = matchesTerm(book.getAuthor(), searchTerm);

                    return matchesTitle || matchesAuthor;
                });
        add(new H1("Książki do wypożyczenia"),searchField, grid
        );
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }


    private void updateList() {
        grid.setItems(service.findAll());
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        Grid.Column<Book> title = grid.addColumn(book -> book.getTitle()).setHeader("Tytuł").setSortable(true);
        Grid.Column<Book> author = grid.addColumn(book -> book.getAuthor()).setHeader("Autor").setSortable(true);
        grid.addColumn(book -> book.getPublished()).setHeader("Data publikacji").setSortable(true);
        grid.addColumn(book -> book.getPages()).setHeader("Ilość stron").setSortable(true);
        grid.addColumn(createStatusComponentRenderer()).setHeader("Dostępność")
                .setAutoWidth(true).setSortable(true).setComparator(book->book.getNumberOf());
        grid.addComponentColumn(book -> {Button editButton = new Button("Wypożycz");
            if(isAvailableBook(book)=="Niedostępna"){
                editButton.setVisible(false);
            }
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(e -> { Wypozycz(book, service);
            });
            return editButton;
        });
//        HeaderRow headerRow = grid.appendHeaderRow();
//        List<Book> people = (List<Book>) service.findAll();
//        GridListDataView<Book> dataView = grid.setItems(people);
//        BookFilter personFilter = new BookFilter(dataView);
//
//        grid.getHeaderRows().clear();
//
//        headerRow.getCell(title).setComponent(
//                createFilterHeader("Name", personFilter::setTitle));
//        headerRow.getCell(author).setComponent(
//                createFilterHeader("Email", personFilter::setAuthor));
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

//    private static VerticalLayout createFilterHeader(String labelText,
//                                                     Consumer<String> filterChangeConsumer) {
//        Label label = new Label(labelText);
//        label.getStyle().set("padding-top", "var(--lumo-space-m)")
//                .set("font-size", "var(--lumo-font-size-xs)");
//        TextField textField = new TextField();
//        textField.setValueChangeMode(ValueChangeMode.EAGER);
//        textField.setClearButtonVisible(true);
//        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
//        textField.setWidthFull();
//        textField.getStyle().set("max-width", "100%");
//        textField.addValueChangeListener(
//                e -> filterChangeConsumer.accept(e.getValue()));
//        VerticalLayout layout = new VerticalLayout(label, textField);
//        layout.getThemeList().clear();
//        layout.getThemeList().add("spacing-xs");
//
//        return layout;
//    }


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

//    private static class BookFilter {
//        private final GridListDataView<Book> dataView;
//
//        private String title;
//        private String author;
//
//        public BookFilter(GridListDataView<Book> dataView) {
//            this.dataView = dataView;
//            this.dataView.addFilter(this::test);
//        }
//
//
//        public void setTitle(String title) {
//            this.title = title;
//            this.dataView.refreshAll();
//        }
//
//        public void setAuthor(String author) {
//            this.author = author;
//            this.dataView.refreshAll();
//        }
//
//        public boolean test(Book book) {
//            boolean matchesTitle = matches(book.getTitle(), title);
//            boolean matchesAuthor = matches(book.getAuthor(), author);
//
//            return matchesTitle && matchesAuthor;
//        }
//
//        private boolean matches(String value, String searchTerm) {
//            return searchTerm == null || searchTerm.isEmpty()
//                    || value.toLowerCase().contains(searchTerm.toLowerCase());
//        }
//    }


}

