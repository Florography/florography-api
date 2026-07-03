package com.korit.florographyapi.user.controller;

import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.service.LinkTokenService;
import com.korit.florographyapi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserMapper userMapper;
    private final LinkTokenService linkTokenService;

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of("google", "naver", "kakao");

    /**
     * 소셜 계정 연동 시작
     * 프론트에서 이 API를 호출하면 link_token이 포함된 OAuth2 인증 URL을 반환합니다.
     * 프론트는 반환된 URL로 리다이렉트하여 소셜 로그인을 진행합니다.
     */
    @PostMapping("/user/link/{provider}")
    public ResponseEntity<?> startLink(
            @PathVariable String provider,
            Authentication authentication
    ) {
        if (!SUPPORTED_PROVIDERS.contains(provider)) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "지원하지 않는 소셜 제공자입니다: " + provider
            ));
        }

        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "유저를 찾을 수 없습니다."));
        }

        // 이미 해당 provider로 연동되어 있는지 확인
        ProviderUser existing = userMapper.selectByProviderAndUid(provider, user.getUid());
        if (existing != null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "이미 " + provider + " 계정이 연동되어 있습니다."
            ));
        }

        // link_token 생성
        String linkToken = linkTokenService.createLinkToken(userId, user.getUid());

        // 프론트가 리다이렉트할 OAuth2 URL 반환
        String oauth2Url = "/oauth2/authorization/" + provider + "?link_token=" + linkToken;

        return ResponseEntity.ok(Map.of(
                "linkUrl", oauth2Url
        ));
    }

    /**
     * 연동된 소셜 계정 목록 조회
     */
    @GetMapping("/user/linked-accounts")
    public ResponseEntity<?> getLinkedAccounts(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "유저를 찾을 수 없습니다."));
        }

        List<ProviderUser> providers = userMapper.selectProvidersByUid(user.getUid());

        // providerId 같은 민감 정보는 제외하고 provider, email만 반환
        List<Map<String, String>> result = providers.stream()
                .map(p -> Map.of(
                        "provider", p.getProvider(),
                        "email", p.getEmail() != null ? p.getEmail() : ""
                ))
                .toList();

        return ResponseEntity.ok(Map.of("linkedAccounts", result));
    }

    /**
     * 소셜 계정 연동 해제
     * 최소 1개의 소셜 계정은 남아 있어야 합니다.
     */
    @DeleteMapping("/user/link/{provider}")
    public ResponseEntity<?> unlinkAccount(
            @PathVariable String provider,
            Authentication authentication
    ) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "유저를 찾을 수 없습니다."));
        }

        // 해당 provider로 연동되어 있는지 확인
        ProviderUser existing = userMapper.selectByProviderAndUid(provider, user.getUid());
        if (existing == null) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", provider + " 계정이 연동되어 있지 않습니다."
            ));
        }

        // 최소 1개 provider는 유지
        int providerCount = userMapper.countProvidersByUid(user.getUid());
        if (providerCount <= 1) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "최소 1개의 소셜 계정이 연동되어 있어야 합니다."
            ));
        }

        userMapper.deleteProviderByProviderAndUid(provider, user.getUid());

        return ResponseEntity.ok(Map.of(
                "message", provider + " 계정 연동이 해제되었습니다."
        ));
    }
}
