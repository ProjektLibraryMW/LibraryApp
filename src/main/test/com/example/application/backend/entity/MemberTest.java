package com.example.application.backend.entity;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;

import static com.example.application.backend.entity.Role.ADMIN;
import static com.example.application.backend.entity.Role.USER;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    Member user1;
    @Test
    public void NoArgsConstructorShouldCreateUserWithNullFields() {
        user1 = new Member();
        assertNull(user1.getRole());
        assertNull(user1.getUsername());
    }

    @Test
    public void UserCreationWithoutIDShouldCreateUserWithGeneratedID() {
        user1 = new Member("test", "haslo", USER);
        assertEquals("test", user1.getUsername());
        assertNotNull(user1.getId());
    }

    @Test
    public void UserPasswordShouldEqualHashedPassword() {
        user1 = new Member("test", "haslo", USER);
        assertEquals(user1.getPasswordHash(), DigestUtils.sha1Hex("haslo" + user1.getPasswordSalt()));
        assertTrue(user1.checkPassword("haslo"));
    }

    @Test
    public void SettingNewRoleShouldChangeIt() {
        user1 = new Member("test", "haslo", USER);
        var oldRole = user1.getRole();
        user1.setRole(ADMIN);
        assertNotEquals(oldRole, user1.getRole());
    }
}