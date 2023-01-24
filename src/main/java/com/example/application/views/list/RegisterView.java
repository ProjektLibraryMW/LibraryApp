package com.example.application.views.list;

import com.example.application.backend.service.AuthService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends Composite {

    private final AuthService authService;

    public RegisterView(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected Component initContent() {
        TextField username = new TextField("Username");
        PasswordField password = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        return new VerticalLayout(
                new H2("Register"),
                username,
                password,
                password2,
                new Button("Confirm", event -> register(username.getValue(),
                        password.getValue(),
                        password2.getValue()))
        );
    }
    private void register(String username, String password1, String password2) {
        if(username.trim().isEmpty()) {
            Notification.show("Enter a username");
        }
        else if (password1.trim().isEmpty()) {
            Notification.show("Enter a password");
        }
        authService.register("admin", "admin");
    }
}
