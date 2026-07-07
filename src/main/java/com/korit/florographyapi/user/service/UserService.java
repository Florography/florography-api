package com.korit.florographyapi.user.service;

import com.korit.florographyapi.dto.ApiResponse;
import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.service.LinkTokenService;
import com.korit.florographyapi.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final LinkTokenService linkTokenService;

    private static final Set<String> SUPPORTED_PROVIDERS = Set.of("google", "naver", "kakao");


    public ApiResponse<Map<String, String>> startLink(String provider, Authentication authentication){
        if (!SUPPORTED_PROVIDERS.contains(provider)) {
            return (ApiResponse.fail(Map.of(
                    "error", "지원하지 않는 소셜 제공자입니다: " + provider
            )));
        }

        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return (ApiResponse.fail(Map.of("error", "유저를 찾을 수 없습니다.")));
        }

        // 이미 해당 provider로 연동되어 있는지 확인
        ProviderUser existing = userMapper.selectByProviderAndUid(provider, user.getUid());
        if (existing != null) {
            return (ApiResponse.fail(Map.of(
                    "error", "이미 " + provider + " 계정이 연동되어 있습니다."
            )));
        }

        // link_token 생성
        String linkToken = linkTokenService.createLinkToken(userId, user.getUid());

        // 프론트가 리다이렉트할 OAuth2 URL 반환
        String oauth2Url = "/oauth2/authorization/" + provider + "?link_token=" + linkToken;

        return ApiResponse.success(Map.of(
                "linkUrl", oauth2Url
        ));
    }

    public ApiResponse<?> getLinkedAccounts(Authentication authentication) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return (ApiResponse.fail(Map.of("error", "유저를 찾을 수 없습니다.")));
        }

        List<ProviderUser> providers = userMapper.selectProvidersByUid(user.getUid());

        // providerId 같은 민감 정보는 제외하고 provider, email만 반환
        List<Map<String, String>> result = providers.stream()
                .map(p -> Map.of(
                        "uid", user.getUid(),
                        "provider", p.getProvider(),
                        "nickname", user.getNickname(),
                        "email", p.getEmail() != null ? p.getEmail() : ""

                ))
                .toList();

        return (ApiResponse.success(Map.of("linkedAccounts", result)));
    }
    public ApiResponse<?> unlinkAccount(String provider, Authentication authentication ) {
        Long userId = (Long) authentication.getPrincipal();
        User user = userMapper.selectById(userId);

        if (user == null) {
            return ApiResponse.fail(Map.of("error", "유저를 찾을 수 없습니다."));
        }

        // 해당 provider로 연동되어 있는지 확인
        ProviderUser existing = userMapper.selectByProviderAndUid(provider, user.getUid());
        if (existing == null) {
            return ApiResponse.fail(Map.of(
                    "error", provider + " 계정이 연동되어 있지 않습니다."
            ));
        }

        // 최소 1개 provider는 유지
        int providerCount = userMapper.countProvidersByUid(user.getUid());
        if (providerCount <= 1) {
            return ApiResponse.fail(Map.of(
                    "error", "최소 1개의 소셜 계정이 연동되어 있어야 합니다."
            ));
        }

        userMapper.deleteProviderByProviderAndUid(provider, user.getUid());

        return ApiResponse.success(Map.of(
                "message", provider + " 계정 연동이 해제되었습니다."
        ));
    }
}
