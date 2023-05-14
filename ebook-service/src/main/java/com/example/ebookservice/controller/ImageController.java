package com.example.ebookservice.controller;

import com.example.ebookdatamodel.entity.Image;
import com.example.ebookservice.dto.ImageDTO;
import com.example.ebookservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(path = "/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<List<ImageDTO>> getAll() {

        return ResponseEntity.ok(imageService.getAllImg());
    }

    @GetMapping("/{imageId}")
    public ResponseEntity<?> getImage(
            @PathVariable Integer imageId
    ) {

        return imageService.getImageById(imageId);
    }

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("name") String name,
            @RequestParam("bid") Integer bid
    ) {

        return ResponseEntity.ok(imageService.addImage(file, name, bid));
    }

    @PutMapping
    public ResponseEntity<Image> updateImage(
            @RequestBody ImageDTO image
    ) {

        return ResponseEntity.ok(imageService.updateImage(image));
    }

    @DeleteMapping("/{imageId}")
    public void deleteImage(
            @PathVariable Integer imageId
    ) {
        imageService.deleteImage(imageId);
    }
}
