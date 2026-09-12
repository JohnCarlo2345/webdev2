package com.johncarlo.webdev2.model;

import jakarta.validation.constraints.*;

public class Book {

    private Long id;

    @NotBlank(message = "Title is required — cannot be empty")
    @Size(min = 2, max = 100, message = "Title must be 2–100 characters long")
    private String title;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 50, message = "Author name must be 2–50 characters long")
    private String author;

    @Min(value = 1000, message = "Publication year must be 1000 or later")
    @Max(value = 2026, message = "Publication year cannot exceed 2026")
    private int publicationYear;

    @NotBlank(message = "Genre is required")
    private String genre;

    // Constructors
    public Book() {}

    public Book(Long id, String title, String author, int publicationYear, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.genre = genre;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getPublicationYear() { return publicationYear; }
    public void setPublicationYear(int publicationYear) { this.publicationYear = publicationYear; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
}

