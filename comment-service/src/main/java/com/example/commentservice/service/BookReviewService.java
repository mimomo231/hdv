package com.example.commentservice.service;

import com.example.commentdatamodel.entity.BookReview;
import com.example.commentservice.request.AddReviewRequest;

public interface BookReviewService {
    Iterable<BookReview> getAll();
    Iterable<BookReview> getByBookId(Integer bookId);
    BookReview addReview(Long userId, AddReviewRequest reviewRequest);
    BookReview updateReview(BookReview bookReview);
    void deleteReview(Integer bookId);
}
