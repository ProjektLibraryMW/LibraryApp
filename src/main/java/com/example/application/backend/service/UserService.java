package com.example.application.backend.service;

import com.example.application.backend.entity.Member;
import com.example.application.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class UserService implements CrudListener<Member>{

    private final UserRepository repository;
    @Override
    public Collection<Member> findAll() {
        return repository.findAll();
    }

    @Override
    public Member add(Member member) {
        return repository.save(member);
    }

    @Override
    public Member update(Member member) {
        return repository.save(member);
    }

    @Override
    public void delete(Member member) {
        repository.delete(member);
    }
}
