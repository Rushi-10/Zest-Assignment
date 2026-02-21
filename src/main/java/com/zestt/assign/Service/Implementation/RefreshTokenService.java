package com.zestt.assign.Service.Implementation;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;

import com.zestt.assign.Entity.RefreshToken;
import com.zestt.assign.Entity.User;
import com.zestt.assign.Repository.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

       private final RefreshTokenRepository refreshTokenRepository;

    private final long REFRESH_DURATION_MS =
            1000L * 60 * 60 * 24 * 7; // 7 days

    // Create new refresh token
    @Transactional
    public RefreshToken createRefreshToken(User user) {

        refreshTokenRepository.deleteByUser(user);
        refreshTokenRepository.flush();

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now()
                        .plusMillis(REFRESH_DURATION_MS))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    // Verify token
    public RefreshToken verifyExpiration(RefreshToken token) {

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }

        return token;
    }

}
