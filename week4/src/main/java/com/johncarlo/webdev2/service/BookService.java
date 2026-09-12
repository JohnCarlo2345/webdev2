package main.java.com.johncarlo.webdev2.service;

import com.webdev2.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final List<Book> books = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public BookService() {
        books.add(new Book(1L, "The Great Gatsby", "F. Scott Fitzgerald", 450.0));
        books.add(new Book(2L, "1984", "George Orwell", 320.0));
        books.add(new Book(3L, "Animal Farm", "George Orwell", 280.0));
        nextId.set(4);
    }

    public List<Book> getAll() {
        return new ArrayList<>(books);
    }

    public List<Book> getByAuthor(String author) {
        if (author == null || author.isBlank()) {
            return getAll();
        }
        return books.stream()
                .filter(b -> author.equals(b.getAuthor()))
                .collect(Collectors.toList());
    }

    public Book getById(Long id) {
        return books.stream()
                .filter(b -> id.equals(b.getId()))
                .findFirst()
                .orElse(null);
    }

    public Book create(Book book) {
        book.setId(nextId.getAndIncrement());
        books.add(book);
        return book;
    }
}
