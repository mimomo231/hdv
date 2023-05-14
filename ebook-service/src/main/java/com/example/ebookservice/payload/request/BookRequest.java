package com.example.ebookservice.payload.request;

import com.example.ebookservice.dto.ImageDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class BookRequest {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("author")
    private String author;

    @JsonProperty("publisher")
    private String publisher;

    @JsonProperty("publish_year")
    private String publishYear;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("number_sales")
    private Integer numberSales;

    @JsonProperty("description")
    private String description;

    @JsonProperty("quantity")
    private Integer quantity;

    @JsonProperty("category_id")
    private Integer categoryId;

    @JsonProperty("images")
    private List<ImageDTO> images;
}
