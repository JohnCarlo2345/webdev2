package main.java.com.johncarlo.webdev2.controller;

import com.webdev2.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
@RequestMapping("/books")
public class BookController {
    private final List<Book> books = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(4);
    public BookController() {
        books.add(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald"));
        books.add(new Book(2L, "1984", "George Orwell"));
        books.add(new Book(3L, "Animal Farm", "George Orwell"));
    }

    @GetMapping
    public String listAll(
            @RequestParam(required = false) String author,
            Model model) {

        List<Book> result;
        if (author != null && !author.isBlank()) {
            result = books.stream()
                    .filter(b -> author.equals(b.getAuthor()))
                    .toList();
        } else {
            result = books;
        }
        model.addAttribute("books", result);
        return "books/list";
    }

    @GetMapping("/{id}")
    public String getDetail(@PathVariable Long id, Model model) {
        Book book = books.stream()
                .filter(b -> id.equals(b.getId()))
                .findFirst()
                .orElse(null);

        model.addAttribute("book", book); // ← exactly what Task 3 wants
        return "book-detail"; // template comes later in Week4
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@ModelAttribute Book book) {
        book.setId(nextId.getAndIncrement());
        books.add(book);
        return "redirect:/books"; // redirect to list
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getDetailWithStatus(@PathVariable Long id) {
        Book book = books.stream()
                .filter(b -> id.equals(b.getId()))
                .findFirst()
                .orElse(null);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(book, HttpStatus.OK); // 200
    }
}
