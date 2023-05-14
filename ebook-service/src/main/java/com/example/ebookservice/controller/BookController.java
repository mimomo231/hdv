package com.example.ebookservice.controller;

import com.example.api.model.GeneralPageResponse;
import com.example.ebookservice.payload.request.BookRequest;
import com.example.ebookservice.payload.response.BookResponse;
import com.example.ebookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/ebook", produces = "application/json")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/all-book")
    public ResponseEntity<GeneralPageResponse<BookResponse>> getAll(Pageable pageable){

        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<BookResponse> getBook(
            @PathVariable Integer bookId
    ) {

        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @GetMapping("/book-cate")
    public ResponseEntity<GeneralPageResponse<BookResponse>> getBooksByCategory(
            @RequestParam(name = "cate-id") Integer cateId,
            Pageable pageable
    ) {

        return ResponseEntity.ok(bookService.getBooksByCategory(cateId, pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> searchBookByName(
            @RequestParam String key,
            @RequestParam Float from,
            @RequestParam Float to
    ) {

        return ResponseEntity.ok(bookService.searchBook(key, from, to));
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<BookResponse> addBook(
            @RequestBody BookRequest bookRequest
    ) {

        return ResponseEntity.ok(bookService.addBook(bookRequest));
    }

    @PutMapping
    public ResponseEntity<BookResponse> updateBook(
            @RequestBody BookRequest book
    ) {

        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Integer bookId
    ) {

        return ResponseEntity.ok(bookService.deleteBook(bookId));
    }
}
