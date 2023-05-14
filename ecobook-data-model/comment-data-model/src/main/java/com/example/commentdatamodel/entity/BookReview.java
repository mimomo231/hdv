package com.example.commentdatamodel.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book_review")
public class BookReview extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "context")
    private String context;

    @Column(name = "product_id")
    private Integer productId;
    
    @Column(name = "user_id")
    private Long userId;
}
