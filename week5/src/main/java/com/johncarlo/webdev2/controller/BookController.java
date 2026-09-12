package com.johncarlo.webdev2.controller;

import com.johncarlo.webdev2.model.Book;
import com.johncarlo.webdev2.service.BookService;
import jakarta.validation.Valid;
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

    // 1. List all books — READ
    @GetMapping
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books/list";
    }

    // 2. Show create form
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "books/form";
    }

    // 3. Process create — CREATE
    @PostMapping("/create")
    public String createBook(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/form";
        }
        bookService.createBook(book);
        return "redirect:/books";
    }

    // 4. Show edit form — pre-populate
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            return "redirect:/books";
        }
        model.addAttribute("book", book);
        return "books/edit-form";
    }

    // 5. Process edit — UPDATE
    @PostMapping("/edit")
    public String updateBook(@Valid @ModelAttribute Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "books/edit-form";
        }
        bookService.updateBook(book);
        return "redirect:/books";
    }

    // 6. Delete — DELETE
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }
}

