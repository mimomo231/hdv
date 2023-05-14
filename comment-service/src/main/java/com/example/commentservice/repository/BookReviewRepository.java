package com.example.commentservice.repository;

import com.example.commentdatamodel.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {
    Iterable<BookReview> getAllByProductId(Integer bookId);
}
