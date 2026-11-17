package com.johncarlo.webdev2.repository;

import com.johncarlo.webdev2.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Page<Author> findByNationalityContaining(String nationality, Pageable pageable);

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Author> findByNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
}

