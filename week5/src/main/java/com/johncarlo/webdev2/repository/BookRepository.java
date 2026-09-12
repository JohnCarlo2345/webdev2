package com.johncarlo.webdev2.repository;

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
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId.getAndIncrement());
        }
        bookStore.put(book.getId(), book);
        return book;
    }

    public List<Book> findAll() {
        return new ArrayList<>(bookStore.values());
    }

    public Book findById(Long id) {
        return bookStore.get(id);
    }

    public Book update(Book book) {
        if (!bookStore.containsKey(book.getId())) {
            bookStore.put(book.getId(), book);
            return null;
        }
        return null;
    }

    public boolean deleteById(Long id) {
        return bookStore.remove(id) != null;
    }
}

