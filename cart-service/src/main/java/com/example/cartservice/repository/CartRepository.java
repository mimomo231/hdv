package com.example.cartservice.repository;

import com.example.cartdatamodel.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT c.* " +
                    "FROM cart c " +
                    "WHERE c.user_id = :userId AND c.status = :status ")
    Cart findByStatusAndUserId(String status, Long userId);
}
