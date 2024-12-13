package org.example.kompletteringsuppgiftajp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Notes_tags")
public class NoteTags {

    @Id
    @GeneratedValue
    private int id;


    //Getters och setters här nedan :D
}
