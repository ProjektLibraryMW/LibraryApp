package com.example.application.backend.repository;

import com.example.application.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

    Member getByUsername(String username);

}
