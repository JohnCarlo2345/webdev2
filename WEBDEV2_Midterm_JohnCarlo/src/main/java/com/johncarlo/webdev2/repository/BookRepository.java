package com.johncarlo.webdev2.repository;

import com.johncarlo.webdev2.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContaining(String title, Pageable pageable);

    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.publicationYear = :year")
    Page<Book> findByPublicationYear(@Param("year") Integer year, Pageable pageable);

    boolean existsByIsbn(String isbn);
}

