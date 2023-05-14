package com.example.userservice.service.impl;

import com.example.api.exception.ForbiddenException;
import com.example.api.exception.NotFoundException;
import com.example.api.util.DateUtils;
import com.example.userdatamodel.entity.RefreshToken;
import com.example.userservice.repository.RefreshTokenRepository;
import com.example.userservice.repository.UserAccountRepository;
import com.example.userservice.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${ecobook.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;
    private final RefreshTokenRepository refreshTokenRepo;
    private final UserAccountRepository userAccountRepo;

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepo.findByToken(token);
    }

    @Override
    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUserAccount(userAccountRepo.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        String.format("createRefreshToken error: Not found User Account with id: %s", userId)
                )
        ));
        refreshToken.setExpiryDate(DateUtils.toTimestamp(Instant.now().plusMillis(refreshTokenDurationMs)));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(DateUtils.toTimestamp(Instant.now())) < 0) {
            refreshTokenRepo.delete(token);
            throw new ForbiddenException(
                    String.format(token.getToken(), "Refresh token was expired. Please make a new sign-in request")
            );
        }

        return token;
    }

    @Override
    public int deleteByUserId(Long userId) {
        return refreshTokenRepo.deleteByUserAccount(userAccountRepo.findById(userId).orElseThrow(
                () -> new NotFoundException(
                        String.format("createRefreshToken error: Not found User Account with id: %s", userId)
                )
        ));
    }
}
