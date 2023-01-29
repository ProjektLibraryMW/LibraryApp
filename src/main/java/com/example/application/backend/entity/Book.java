package com.example.application.backend.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class Book {

    public Book() {

    }

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String author;

    private LocalDate published;

    private Integer pages;

    private Integer numberOf;

    public Book(long id, String title, String author, LocalDate published, Integer pages, Integer numberOf){
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
        this.pages = pages;
        this.numberOf = numberOf;
        this.setNumberOf(numberOf);
    }
    public void setNumberOf(Integer numberOf) {
        if(numberOf<0)
            this.numberOf=0;
        else
            this.numberOf = numberOf;
    }
}
