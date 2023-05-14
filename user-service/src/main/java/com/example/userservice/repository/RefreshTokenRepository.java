package com.example.userservice.repository;

import com.example.userdatamodel.entity.RefreshToken;
import com.example.userdatamodel.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    int deleteByUserAccount(UserAccount userAccount);
}
