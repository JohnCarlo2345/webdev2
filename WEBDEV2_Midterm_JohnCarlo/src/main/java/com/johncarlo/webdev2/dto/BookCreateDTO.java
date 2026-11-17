package com.johncarlo.webdev2.dto;

import jakarta.validation.constraints.*;

public class BookCreateDTO {

    @NotBlank(message = "Title is required")
    @Size(max = 200)
    private String title;

    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "^[0-9-]{10,17}$", message = "Invalid ISBN format")
    private String isbn;

    @Min(value = 1000)
    @Max(value = 2100)
    private Integer publicationYear;

    @NotNull(message = "Author ID is required")
    private Long authorId;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public Integer getPublicationYear() { return publicationYear; }
    public void setPublicationYear(Integer publicationYear) { this.publicationYear = publicationYear; }
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
}
