package com.example.ebookservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.api.model.GeneralPageResponse;
import com.example.ebookdatamodel.entity.Book;
import com.example.ebookdatamodel.entity.Category;
import com.example.ebookdatamodel.entity.Image;
import com.example.ebookservice.dto.ImageDTO;
import com.example.ebookservice.payload.request.BookRequest;
import com.example.ebookservice.payload.response.BookResponse;
import com.example.ebookservice.repository.BookRepository;
import com.example.ebookservice.repository.CategoryRepository;
import com.example.ebookservice.repository.ImageRepository;
import com.example.ebookservice.service.BookService;
import com.example.ebookservice.service.CategoryService;
import com.example.ebookservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepo;
    private final CategoryRepository categoryRepo;
    private final ImageRepository imageRepo;
    private final ImageService imageService;
    private final CategoryService categoryService;

    @Override
    public GeneralPageResponse<BookResponse> getAllBooks(Pageable pageable) {

        Page<Book> books = bookRepo.findAll(pageable);
        List<BookResponse> contents = books.stream()
                .map(this::mapToBookResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return GeneralPageResponse.toResponse(
                new PageImpl<>(
                        contents,
                        pageable,
                        books.getTotalElements()
                ));
    }

    @Override
    public BookResponse getBookById(Integer bookId) {

        Book book = bookRepo.findById(bookId).orElseThrow(
                () -> new NotFoundException(
                        String.format("getBookById error: Not found Book with id: %s", bookId)
                )
        );

        if(Objects.nonNull(book)) {

            return mapToBookResponse(book);
        }
        return null;
    }

    @Override
    public GeneralPageResponse<BookResponse> getBooksByCategory(Integer cateId, Pageable pageable) {

        Page<Book> books = bookRepo.findAllByCategoryId(cateId, pageable);
        List<BookResponse> contents = books.stream()
                .map(this::mapToBookResponse)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return GeneralPageResponse.toResponse(
                new PageImpl<>(
                        contents,
                        pageable,
                        books.getTotalElements()
                ));
    }

    @Override
    public List<BookResponse> searchBook(String key, Float from, Float to) {

        List<Book> books = bookRepo.findByNameContaining(key).stream()
                .filter(b -> b.getPrice() <= to && b.getPrice() >= from)
                .collect(Collectors.toList());

        return books.stream()
                .map(this::mapToBookResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookResponse addBook(BookRequest bookRequest) {

        Category category = categoryRepo.findById(bookRequest.getCategoryId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("addBook error: Not found Category with id: %s", bookRequest.getCategoryId())
                )
        );

        List<Integer> imageIds = bookRequest.getImages().stream().map(ImageDTO::getId).collect(Collectors.toList());
        List<Image> images = imageRepo.findByIdIn(imageIds);

        Book book = bookRepo.save(Book.builder()
                .name(bookRequest.getName())
                .author(bookRequest.getAuthor())
                .price(bookRequest.getPrice())
                .category(category)
                .images(images)
                .publishYear(bookRequest.getPublishYear())
                .numberSales(bookRequest.getNumberSales())
                .publisher(bookRequest.getPublisher())
                .quantity(bookRequest.getQuantity())
                .description(bookRequest.getDescription())
                .build());

        return mapToBookResponse(book);
    }

    @Override
    public BookResponse updateBook(BookRequest bookRequest) {

        List<Image> images = bookRequest.getImages().stream()
                .map(p -> Image.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .img(p.getImg())
                        .build())
                .collect(Collectors.toList());

        Book book = bookRepo.save(Book.builder()
                .id(bookRequest.getId())
                .name(bookRequest.getName())
                .author(bookRequest.getAuthor())
                .price(bookRequest.getPrice())
                .images(images)
                .publishYear(bookRequest.getPublishYear())
                .numberSales(bookRequest.getNumberSales())
                .publisher(bookRequest.getPublisher())
                .quantity(bookRequest.getQuantity())
                .description(bookRequest.getDescription())
                .build());

        return mapToBookResponse(book);
    }

    @Override
    public String deleteBook(Integer bookId) {

        try{
            bookRepo.deleteById(bookId);
        }catch (EmptyResultDataAccessException e){
            log.info(e.getMessage());
            return "fail";
        }
        return "success";
    }

    private BookResponse mapToBookResponse(Book book) {

        List<ImageDTO> dtos = book.getImages().stream()
                .map(imageService::mapToImageDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return BookResponse.builder()
                        .id(book.getId())
                        .name(book.getName())
                        .author(book.getAuthor())
                        .images(dtos)
                        .publisher(book.getPublisher())
                        .publishYear(book.getPublishYear())
                        .numberSales(book.getNumberSales())
                        .price(book.getPrice())
                        .quantity(book.getQuantity())
                        .description(book.getDescription())
                        .category(categoryService.mapToCategoryResponse(book.getCategory()))
                        .build();
    }
}
