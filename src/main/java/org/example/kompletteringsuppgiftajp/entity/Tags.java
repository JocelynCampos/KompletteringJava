package org.example.kompletteringsuppgiftajp.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "Tags")

public class Tags {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "tag_content")
    private String tagContent;

    public Tags () {
    }

    public Tags (String tagContent) {
        this.tagContent = tagContent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagContent() {
        return tagContent;
    }

    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }
}
