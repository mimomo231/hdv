package com.example.commentservice.controller;

import com.example.api.controller.BaseController;
import com.example.commentdatamodel.entity.BookReview;
import com.example.commentservice.request.AddReviewRequest;
import com.example.commentservice.service.BookReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/book-review", produces = "application/json")
@RequiredArgsConstructor
public class BookReviewController extends BaseController {

    private final BookReviewService bookReviewService;

    @GetMapping("/")
    public Iterable<BookReview> getAll() {

        return bookReviewService.getAll();
    }

    @GetMapping("/{bookId}")
    public Iterable<BookReview> getByBookId(
            @PathVariable Integer bookId
    ) {

        return bookReviewService.getByBookId(bookId);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookReview addBook(
            @RequestBody AddReviewRequest reviewRequest
    ) {

        Long userId = getOriginalId();

        return bookReviewService.addReview(userId, reviewRequest);
    }

    @PutMapping("/")
    public BookReview updateBook(
            @RequestBody BookReview bookReview
    ) {

        return bookReviewService.updateReview(bookReview);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(
            @PathVariable Integer bookId
    ) {

        bookReviewService.deleteReview(bookId);
    }
}