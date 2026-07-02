package com.korit.florographyapi.service;

import com.korit.florographyapi.entity.LinkToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LinkTokenService {

    private final ConcurrentHashMap<String, LinkToken> tokenStore = new ConcurrentHashMap<>();

    private static final long EXPIRATION_MINUTES = 5;

    /**
     * 연동용 임시 토큰 생성
     */
    public String createLinkToken(Long userId, String uid) {
        // 만료된 토큰 정리
        cleanExpiredTokens();

        String token = UUID.randomUUID().toString();
        LinkToken linkToken = LinkToken.builder()
                .token(token)
                .userId(userId)
                .uid(uid)
                .expiresAt(LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES))
                .build();

        tokenStore.put(token, linkToken);
        return token;
    }

    /**
     * 토큰 검증 후 소비 (일회용)
     * 유효하면 LinkToken 반환, 아니면 null
     */
    public LinkToken validateAndConsume(String token) {
        if (token == null) {
            return null;
        }

        LinkToken linkToken = tokenStore.remove(token);
        if (linkToken == null || linkToken.isExpired()) {
            return null;
        }

        return linkToken;
    }

    /**
     * 만료된 토큰 정리
     */
    private void cleanExpiredTokens() {
        tokenStore.entrySet().removeIf(entry -> entry.getValue().isExpired());
    }
}
