package com.korit.florographyapi.user.controller;

import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.service.LinkTokenService;
import com.korit.florographyapi.user.service.UserService;
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
    private final UserService userService;


    /**
     * 소셜 계정 연동 시작
     * 프론트에서 이 API를 호출하면 link_token이 포함된 OAuth2 인증 URL을 반환합니다.
     * 프론트는 반환된 URL로 리다이렉트하여 소셜 로그인을 진행합니다.
     */
    @PostMapping("/user/link/{provider}")
    public ResponseEntity<ApiResponse<Map<String, String>>> startLink(@PathVariable String provider, Authentication authentication) {
        return ResponseEntity.ok(userService.startLink(provider, authentication));
    }

    /**
     * 연동된 소셜 계정 목록 조회
     */
    @GetMapping("/user/linked-accounts")
    public ResponseEntity<ApiResponse<?>> getLinkedAccounts(Authentication authentication) {

        return ResponseEntity.ok(userService.getLinkedAccounts(authentication));
    }

    /**
     * 소셜 계정 연동 해제
     * 최소 1개의 소셜 계정은 남아 있어야 합니다.
     */
    @DeleteMapping("/user/link/{provider}")
    public ResponseEntity<ApiResponse<?>> unlinkAccount(@PathVariable String provider, Authentication authentication) {
        return ResponseEntity.ok(userService.unlinkAccount(provider, authentication));
    }
}
