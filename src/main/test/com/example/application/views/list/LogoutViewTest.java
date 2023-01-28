package com.example.application.views.list;

import com.vaadin.flow.component.UI;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogoutViewTest {

    @Test
    public void LogoutTest() {
        UI.getCurrent().getPage().setLocation("login");
        System.out.println(UI.getCurrent().getPage());
    }
}