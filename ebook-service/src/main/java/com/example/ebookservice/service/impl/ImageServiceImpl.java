package com.example.ebookservice.service.impl;

import com.example.api.exception.NotFoundException;
import com.example.ebookdatamodel.entity.Book;
import com.example.ebookdatamodel.entity.Image;
import com.example.ebookservice.dto.ImageDTO;
import com.example.ebookservice.payload.response.BookResponse;
import com.example.ebookservice.repository.BookRepository;
import com.example.ebookservice.repository.ImageRepository;
import com.example.ebookservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepo;

    private final BookRepository bookRepo;

    @Override
    public List<ImageDTO> getAllImg() {
        return imageRepo.findAll().stream()
                .map(this::mapToImageDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<?> getImageById(Integer imageId) {

        boolean jpgCheck = false;
        String imgDir ="D:/image/anya.png";
        Optional<Image> image = imageRepo.findById(imageId);
        if (image.isPresent()) {
            if(image.get().getImg().matches("^[^\\.]*\\.[^\\.]*$")){
                imgDir = imgDir.substring(0,9)+image.get().getImg();
                if(image.get().getImg().split("\\.")[1].equals("jpg")){
                    jpgCheck = true;
                }
            }
        }

        byte[] imageData = new byte[0];
        try {
            File file = new File(imgDir);
            BufferedImage i = ImageIO.read(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            if (jpgCheck) {
                ImageIO.write(i, "jpg", outputStream);
            } else {
                ImageIO.write(i, "png", outputStream);
            }

            imageData = outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HttpHeaders headers = new HttpHeaders();
        if (jpgCheck){
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else {
            headers.setContentType(MediaType.IMAGE_PNG);
        }
        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }

    @Override
    public String addImage(MultipartFile file, String name, Integer bid) {

        String fileName = file.getOriginalFilename();
        try {
            // Save the file to the server
            File destinationFile = new File("D:/image/" + fileName);
            file.transferTo(destinationFile);
            Image i = new Image();
            i.setImg(fileName);
            i.setName(name);

            Book b = new Book();
            b.setId(bid);
            i.setBook(b);

            imageRepo.save(i);
            return "File uploaded successfully: " + fileName;
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }

    @Override
    public Image updateImage(ImageDTO imageDTO) {

        Book book = bookRepo.findById(imageDTO.getBookId()).orElseThrow(
                () -> new NotFoundException(
                        String.format("updateImage error: Not found Book with id: %s", imageDTO.getBookId())
                )
        );

        Image image = Image.builder()
                .id(imageDTO.getId())
                .name(imageDTO.getName())
                .img(imageDTO.getImg())
                .book(book)
                .build();
        return imageRepo.save(image);
    }

    @Override
    public void deleteImage(Integer imageId) {

        try {
            imageRepo.deleteById(imageId);
        } catch (EmptyResultDataAccessException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public ImageDTO mapToImageDTO(Image image) {

        return ImageDTO.builder()
                .id(image.getId())
                .img(image.getImg())
                .name(image.getName())
                .bookId(image.getBook().getId())
                .build();
    }
}
