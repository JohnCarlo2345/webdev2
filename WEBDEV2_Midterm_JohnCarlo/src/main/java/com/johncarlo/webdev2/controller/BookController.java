package com.johncarlo.webdev2.controller;

import com.johncarlo.webdev2.dto.BookCreateDTO;
import com.johncarlo.webdev2.dto.BookDTO;
import com.johncarlo.webdev2.entity.Book;
import com.johncarlo.webdev2.exception.ResourceNotFoundException;
import com.johncarlo.webdev2.exception.IllegalStateException;
import com.johncarlo.webdev2.AuthorRepository;
import com.johncarlo.webdev2.repository.BookRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@Transactional
public class BookController {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookController(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@Valid @RequestBody BookCreateDTO dto) {
        if (bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalStateException("Book with ISBN " + dto.getIsbn() + " already exists."); // 409
        }
        var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        Book book = new Book(dto.getTitle(), dto.getIsbn(), dto.getPublicationYear());
        book.setAuthor(author);
        Book saved = bookRepository.save(book);
        return new ResponseEntity<>(toDTO(saved), HttpStatus.CREATED); // 201
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> list(
            @PageableDefault(size = 10, sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Integer year) {

        Page<Book> page;
        if (title != null && !title.isBlank()) {
            page = bookRepository.findByTitleContaining(title, pageable);
        } else if (authorId != null) {
            page = bookRepository.findByAuthorId(authorId, pageable);
        } else if (year != null) {
            page = bookRepository.findByPublicationYear(year, pageable);
        } else {
            page = bookRepository.findAll(pageable);
        }
        return ResponseEntity.ok(page.map(this::toDTO)); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable Long id) {
        return bookRepository.findById(id)
                .map(b -> ResponseEntity.ok(toDTO(b)))
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id)); // 404
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable Long id, @Valid @RequestBody BookCreateDTO dto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        if (!book.getIsbn().equals(dto.getIsbn()) && bookRepository.existsByIsbn(dto.getIsbn())) {
            throw new IllegalStateException("Book with ISBN " + dto.getIsbn() + " already exists."); // 409
        }

        var author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + dto.getAuthorId()));

        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationYear(dto.getPublicationYear());
        book.setAuthor(author);
        return ResponseEntity.ok(toDTO(bookRepository.save(book))); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        bookRepository.delete(book);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }

    private BookDTO toDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getPublicationYear(),
                book.getAuthor().getId(),
                book.getAuthor().getName()
        );
    }
}

