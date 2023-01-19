package com.example.application.backend.service;

import com.example.application.backend.entity.Book;
import com.example.application.backend.entity.User;
import com.example.application.backend.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements CrudListener<User>{

    private final UserRepository repository;
    @Override
    public Collection<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User add(User user) {
        return repository.save(user);
    }

    @Override
    public User update(User user) {
        return repository.save(user);
    }

    @Override
    public void delete(User user) {
        repository.delete(user);
    }
}
