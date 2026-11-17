package com.johncarlo.webdev2.controller;

import com.johncarlo.webdev2.dto.AuthorCreateDTO;
import com.johncarlo.webdev2.dto.AuthorDTO;
import com.johncarlo.webdev2.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> create(@Valid @RequestBody AuthorCreateDTO dto) {
        return new ResponseEntity<>(authorService.create(dto), HttpStatus.CREATED); // 201
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> list(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String nationality) {
        return ResponseEntity.ok(authorService.findAll(pageable, name, nationality)); // 200
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(authorService.findById(id)); // 200
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> update(@PathVariable Long id, @Valid @RequestBody AuthorCreateDTO dto) {
        return ResponseEntity.ok(authorService.update(id, dto)); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}
