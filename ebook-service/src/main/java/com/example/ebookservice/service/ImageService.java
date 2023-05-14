package com.example.ebookservice.service;

import com.example.ebookdatamodel.entity.Image;
import com.example.ebookservice.dto.ImageDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {

    List<ImageDTO> getAllImg();

    ResponseEntity<?> getImageById(Integer imageId);

    String addImage(MultipartFile file, String name, Integer bid);

    Image updateImage(ImageDTO imageDTO);

    void deleteImage(Integer imageId);

    ImageDTO mapToImageDTO(Image image);
}
