package com.example.commentservice.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddReviewRequest {

    @JsonProperty("context")
    private String context;

    @JsonProperty("product_id")
    private Integer productId;
}
