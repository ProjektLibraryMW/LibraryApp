package com.example.application.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Book {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

    private String title;

    private String author;

    private LocalDate published;

    private Integer pages;

    private Integer numberOf;


    public void setNumberOf(Integer numberOf) {
        if(numberOf<0)
            this.numberOf=0;
        else
            this.numberOf = numberOf;
    }
}
