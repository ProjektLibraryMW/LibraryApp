package com.example.application.backend.repository;

import com.example.application.backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b " +
            "where lower(b.title) like lower(concat('%', :searchTerm, '%')) ")
    List<Book> search(@Param("searchTerm") String searchTerm);

}
