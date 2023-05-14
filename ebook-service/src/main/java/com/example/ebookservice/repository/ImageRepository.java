package com.example.ebookservice.repository;

import com.example.ebookdatamodel.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer> {

    List<Image> findByIdIn(Collection<Integer> ids);
}
