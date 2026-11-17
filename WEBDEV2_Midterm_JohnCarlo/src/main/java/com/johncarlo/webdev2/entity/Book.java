package com.johncarlo.webdev2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "books", uniqueConstraints = {
    @UniqueConstraint(columnNames = "isbn")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, length = 13)
    private String isbn;

    private Integer publicationYear;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book() {}
    public Book(String title, String isbn, Integer publicationYear) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
}

