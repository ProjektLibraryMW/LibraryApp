package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import org.vaadin.crudui.crud.CrudOperation;
import org.vaadin.crudui.crud.impl.GridCrud;
import org.vaadin.crudui.form.impl.form.factory.DefaultCrudFormFactory;
import org.vaadin.crudui.layout.impl.AbstractTwoComponentsCrudLayout;
import org.vaadin.crudui.layout.impl.HorizontalSplitCrudLayout;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@Route(value="admin", layout = MainView.class)
@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    public AdminView(BookService service) {
        DefaultCrudFormFactory<Book> formFactory = new DefaultCrudFormFactory<Book>(Book.class) {
            @Override
            protected void configureForm(FormLayout formLayout, List<HasValueAndElement> fields) {
                Component nameField = (Component) fields.get(0);
                formLayout.setColspan(nameField, 2);
            }
        };
        var crud = new GridCrud<>(Book.class, new HorizontalSplitCrudLayout(), formFactory);
        crud.setClickRowToUpdate(true);
        crud.setUpdateOperationVisible(false);
        crud.getFindAllButton().setTooltipText("Odśwież listę");
        crud.getAddButton().setTooltipText("Dodaj element");
        crud.getDeleteButton().setTooltipText("Usuń element");
        crud.getGrid().setColumns();
        crud.getGrid().addColumn("title").setHeader("Tytuł");
        crud.getGrid().addColumn("author").setHeader("Autor");
        crud.getGrid().addColumn("published").setHeader("Data publikacji");
        crud.getGrid().addColumn("pages").setHeader("Ilość stron");
        crud.getGrid().addColumn("numberOf").setHeader("Ilość egzemplarzy");
        crud.getCrudFormFactory().setVisibleProperties("title", "author", "published", "pages", "numberOf");
        crud.getCrudFormFactory().setFieldCaptions("Tytuł", "Autor", "Data wydania", "Ilość stron", "Ilość egzemplarzy");
        formFactory.setButtonCaption(CrudOperation.ADD, "Dodaj książkę");
        formFactory.setButtonCaption(CrudOperation.UPDATE, "Zaktualizuj");
        formFactory.setButtonCaption(CrudOperation.DELETE, "Usuń");
        formFactory.setCancelButtonCaption("Anuluj");
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        ((AbstractTwoComponentsCrudLayout) crud.getCrudLayout()).setFormCaption(CrudOperation.DELETE, "Czy na pewno chcesz usunąć ten tytuł z listy?");

        add(
                new H1("Panel administracyjny"),
                crud
        );

        crud.setOperations(
                () -> service.findAll(),
                book -> service.add(book),
                book -> service.update(book),
                book -> service.delete(book)
        );
    }

//    @Override
//    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
//
//    }
}
