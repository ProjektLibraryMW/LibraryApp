package com.example.application.backend.entity;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RoleTest {


    @Test
    public void SpecifiedRoleShouldNotBeNull() {
        assertNotNull(Role.ADMIN);
    }

}