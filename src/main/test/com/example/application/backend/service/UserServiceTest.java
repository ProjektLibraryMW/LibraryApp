package com.example.application.backend.service;

import com.example.application.backend.entity.Book;
import com.example.application.backend.entity.Member;
import com.example.application.backend.entity.Role;
import com.example.application.backend.repository.BookRepository;
import com.example.application.backend.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;


    @InjectMocks
    private UserService userService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void findAll() {

    }

    @Test
    public void add() {
        Member user1 = new Member("user1", "password", Role.USER);
        userService.add(user1);
        System.out.println(userRepository.findById(0L));
    }

    @Test
    public void update() {
        Member user1 = new Member("user1", "password", Role.USER);
        userService.add(user1);
        user1.setRole(Role.ADMIN);
        userService.update(user1);
        System.out.println(userRepository.findById(0L));
        userService.findAll();
    }

    @Test
    public void delete() {
        Member user1 = new Member("user1", "password", Role.USER);
        userService.add(user1);
        System.out.println(userRepository.findById(0L));
        userService.delete(user1);
        userService.findAll();
    }
}