package com.johncarlo.webdev2.service;

import com.johncarlo.webdev2.model.Book;
import com.johncarlo.webdev2.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    // Read — all
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Read — single
    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    // Update
    public Book updateBook(Book book) {
        return bookRepository.update(book);
    }

    // Delete
    public boolean deleteBook(Long id) {
        return bookRepository.deleteById(id);
    }
}

