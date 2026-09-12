package main.java.com.johncarlo.webdev2.repository;

import com.johncarlo.webdev2.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class BookRepository {

    private final Map<Long, Book> bookStore = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    // Create
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId.getAndIncrement());
        }
        bookStore.put(book.getId(), book);
        return book;
    }

    // Read — all
    public List<Book> findAll() {
        return new ArrayList<>(bookStore.values());
    }

    // Read — single
    public Book findById(Long id) {
        return bookStore.get(id);
    }

    // Update
    public Book update(Book book) {
        if (!bookStore.containsKey(book.getId())) {
            return null;
        }
        bookStore.put(book.getId(), book);
        return book;
    }

    // Delete
    public boolean deleteById(Long id) {
        return bookStore.remove(id) != null;
    }
}

