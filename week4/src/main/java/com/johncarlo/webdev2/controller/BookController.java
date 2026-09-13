package com.johncarlo.webdev2.controller;

import com.webdev2.model.Book;
import com.webdev2.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    public String listBooks(
            @RequestParam(required = false) String author,
            Model model) {
        model.addAttribute("books", bookService.getByAuthor(author));
        return "books";
    }

    public ResponseEntity<String> bookDetail(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        model.addAttribute("book", book);
        return new ResponseEntity<>("book-detail", HttpStatus.OK);
    }

    public String showForm(@ModelAttribute("book") Book book) {
        return "book-form";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createBook(
            @Valid @ModelAttribute("book") Book book,
            BindingResult result) {
        if (result.hasErrors()) {
            return "book-form";
        }
        bookService.create(book);
        return "redirect:/books";
    }
}

