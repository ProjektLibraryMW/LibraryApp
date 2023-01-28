package com.example.application.views.list;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.awt.*;

@Route("login")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private LoginI18n login = LoginI18n.createDefault();
    private LoginForm loginForm = new LoginForm();

    public LoginView() {
        addClassName("login-view");
        setSizeFull();

        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(Alignment.CENTER);

        LoginI18n.Form i18 = login.getForm();
        i18.setTitle("Logowanie do panelu administracyjnego");
        i18.setUsername("Login");
        i18.setPassword("Hasło");
        i18.setSubmit("Zaloguj się");
        LoginI18n.ErrorMessage i18error = login.getErrorMessage();
        i18error.setTitle("Nieprawidłowe dane");
        i18error.setMessage("Sprawdź czy wprowadzono prawidłowe dane i spróbuj ponownie");
        login.setErrorMessage(i18error);
        loginForm.setI18n(login);
        loginForm.setForgotPasswordButtonVisible(false);
        loginForm.setAction("login");

        add(loginForm);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        if(beforeEnterEvent.getLocation()
                .getQueryParameters()
                .getParameters()
                .containsKey("error")) {
            loginForm.setError(true);
        }
    }
}