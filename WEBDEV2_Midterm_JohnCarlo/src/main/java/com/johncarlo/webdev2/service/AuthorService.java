package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.dto.AuthorCreateDTO;
import com.johncarlo.webdev2.dto.AuthorDTO;
import com.johncarlo.webdev2.dto.BookDTO;
import com.johncarlo.webdev2.entity.Author;
import com.johncarlo.webdev2.entity.Book;
import com.johncarlo.webdev2.exception.ResourceNotFoundException;
import com.johncarlo.webdev2.repository.AuthorRepository;
import com.johncarlo.webdev2.repository.BookRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    // Create
    public AuthorDTO create(AuthorCreateDTO dto) {
        Author author = new Author(dto.getName(), dto.getNationality());
        return toDTO(authorRepository.save(author));
    }

    // Read all with pagination
    @Transactional(readOnly = true)
    public Page<AuthorDTO> findAll(Pageable pageable, String name, String nationality) {
        Page<Author> page;
        if (name != null && !name.isBlank()) {
            page = authorRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (nationality != null && !nationality.isBlank()) {
            page = authorRepository.findByNationalityContaining(nationality, pageable);
        } else {
            page = authorRepository.findAll(pageable);
        }
        return page.map(this::toDTO);
    }

    // Read single
    @Transactional(readOnly = true)
    public AuthorDTO findById(Long id) {
        return authorRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    // Update
    public AuthorDTO update(Long id, AuthorCreateDTO dto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        author.setName(dto.getName());
        author.setNationality(dto.getNationality());
        return toDTO(authorRepository.save(author));
    }

    // Delete
    public void delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
        authorRepository.delete(author);
    }

    // Mapper methods
    private AuthorDTO toDTO(Author entity) {
        List<BookDTO> bookDTOs = entity.getBooks().stream()
                .map(this::toBookDTO)
                .collect(Collectors.toList());
        return new AuthorDTO(entity.getId(), entity.getName(), entity.getNationality(), bookDTOs);
    }

    private BookDTO toBookDTO(Book book) {
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

