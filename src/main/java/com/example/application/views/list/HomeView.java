package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.SerializableBiConsumer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@PageTitle("Biblioteka")
@Route("")
@AnonymousAllowed
public class HomeView extends VerticalLayout {

    Grid<Book> grid = new Grid<>(Book.class);

    Map<String, Integer> order = new HashMap<>();

    int numberOfOrders = 0;

    @Autowired
    BookRepository repository;


    Label confirmLabel = new Label("Ilość przedmiotów w koszyku: "+numberOfOrders);
    Button shoppingCartButton = new Button("Potwierdź zamówienie");

    HorizontalLayout layout = new HorizontalLayout(confirmLabel, shoppingCartButton);

    BookService service;
    public HomeView(BookService service) {
        this.service = service;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        List<Book> people = (List<Book>) service.findAll();
        GridListDataView<Book> dataView = grid.setItems(people);

        TextField searchField = new TextField();
        searchField.setWidth("50%");
        searchField.setPlaceholder("Wyszukaj po nazwie lub autorze");
        searchField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
        searchField.setValueChangeMode(ValueChangeMode.EAGER);
        searchField.addValueChangeListener(e -> dataView.refreshAll());

        System.out.println();
        H1 header = new H1("Książki do wypożyczenia");
        VerticalLayout vlayout = new VerticalLayout(header, searchField);
        vlayout.setAlignItems(Alignment.CENTER);
        shoppingCartButton.addClickListener(e -> { Potwierdz();
        });

        layout.setWidthFull();
        layout.setAlignItems(Alignment.CENTER);
        layout.setJustifyContentMode(JustifyContentMode.END);

        dataView.addFilter(book -> {
                    String searchTerm = searchField.getValue().trim();

                    if (searchTerm.isEmpty())
                        return true;

                    boolean matchesTitle = matchesTerm(book.getTitle(),
                            searchTerm);
                    boolean matchesAuthor = matchesTerm(book.getAuthor(), searchTerm);

                    return matchesTitle || matchesAuthor;
                });
        add(vlayout, grid, layout
        );
    }

    private void Potwierdz() {
        for(var entry : order.entrySet()){
           Book book = repository.getByTitle(entry.getKey());
           book.setNumberOf(book.getNumberOf() - entry.getValue());
           service.update(book);
           System.out.println(repository.findAll().stream().count());
           updateList();
        }
        updateList();
        Notification notification = Notification
                .show("Potwierdzono zamówienie!");
        notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        numberOfOrders = 0;
        confirmLabel.setText("Ilość przedmiotów w koszyku: "+numberOfOrders);
    }

    private boolean matchesTerm(String value, String searchTerm) {
        return value.toLowerCase().contains(searchTerm.toLowerCase());
    }


    private void updateList() {
        grid.setItems(service.findAll());
        confirmLabel.setText("Ilość przedmiotów w koszyku: "+numberOfOrders);
    }
    private void configureGrid() {
        grid.addClassNames("contact-grid");
        grid.setSizeFull();
        grid.setColumns();
        Grid.Column<Book> title = grid.addColumn(book -> book.getTitle()).setHeader("Tytuł").setSortable(true);
        Grid.Column<Book> author = grid.addColumn(book -> book.getAuthor()).setHeader("Autor").setSortable(true);
        grid.addColumn(book -> book.getPublished()).setHeader("Data publikacji").setSortable(true);
        grid.addColumn(book -> book.getPages()).setHeader("Ilość stron").setSortable(true);
        Grid.Column<Book> badgeColumn = grid.addColumn(createStatusComponentRenderer()).setHeader("Dostępność")
                .setAutoWidth(true).setSortable(true).setComparator(book->book.getNumberOf());
        Grid.Column<Book> last = grid.addComponentColumn(book -> {Button editButton = new Button("Wypożycz");
            if(isAvailableBook(book)=="Niedostępna"){
                editButton.setVisible(false);
            }
            editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            editButton.addClickListener(e -> { Wypozycz(book, service);
            });
            return editButton;
        });
        grid.getColumns().forEach(col -> col.setAutoWidth(true));
        grid.getColumns().forEach(col -> col.setTextAlign(ColumnTextAlign.CENTER));
        badgeColumn.setWidth("16em").setFlexGrow(0);
        last.setWidth("12em").setFlexGrow(0);
    }


    protected void Wypozycz(Book book, BookService service){

        if(book.getNumberOf()<0) {
        }else {
            System.out.println(book.getNumberOf());
            int k = order.getOrDefault(book.getTitle(), 0);
            if(k>=book.getNumberOf()) {
                Notification notification = Notification
                        .show("Brak takiej ilości książek!");
                notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
            }
            else {
                Notification notification = Notification
                        .show("Dodano do zamówienia!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                numberOfOrders++;
                if(order.containsKey(book.getTitle()))
                    order.put(book.getTitle(), order.get(book.getTitle()) + 1);
                else
                    order.put(book.getTitle(), 1);

            }
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
            text="Dostępna: "+book.getNumberOf() + " szt.";

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

