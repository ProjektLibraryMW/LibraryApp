package com.example.application.views.list;

import com.example.application.backend.entity.Book;
import com.example.application.backend.service.BookService;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.crudui.crud.impl.GridCrud;

import javax.annotation.security.RolesAllowed;

@Route("admin")
//@RolesAllowed("ADMIN")
public class AdminView extends VerticalLayout {
    public AdminView(BookService service) {
        var crud = new GridCrud<>(Book.class, service);
        crud.getGrid().setColumns("title","author", "published", "pages", "numberOf");
        crud.getCrudFormFactory().setVisibleProperties("title", "author", "published", "pages", "numberOf");

        add(
                new H1("Admin view"),
                crud
        );
    }
}
