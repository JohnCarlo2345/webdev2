package com.johncarlo.webdev2.dto;

public class BookDTO {
    private Long id;
    private String title;
    private String isbn;
    private Integer publicationYear;
    private Long authorId;
    private String authorName;

    public BookDTO() {}
    public BookDTO(Long id, String title, String isbn, Integer publicationYear, Long authorId, String authorName) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.authorId = authorId;
        this.authorName = authorName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public String getAuthorName() { return authorName; }
    public void setAuthorName(String authorName) { this.authorName = authorName; }
}
