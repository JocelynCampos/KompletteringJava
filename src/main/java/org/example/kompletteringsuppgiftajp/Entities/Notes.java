package org.example.kompletteringsuppgiftajp.Entities;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note_title", nullable = false)
    private String noteTitle;

    @Column(name = "note_content")
    private String noteContent;

    @ManyToMany
    @JoinTable(
            name = "Notes_Tags",
            joinColumns = @JoinColumn(name = "note_id"), //Referens till Notes table
            inverseJoinColumns = @JoinColumn(name = "tag_id") //Referens till Tag table
    )

    private Set<Tags> tags = new HashSet<>();

    public Notes() {
    //Den tomma konstruktorn
    }

    //Parametiserad konstruktor
    public Notes (String noteTitle, String noteContent) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
    }

    //Getters och setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }
}
