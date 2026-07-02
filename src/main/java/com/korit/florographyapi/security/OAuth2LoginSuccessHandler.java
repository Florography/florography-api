package com.korit.florographyapi.security;

import com.korit.florographyapi.entity.LinkToken;
import com.korit.florographyapi.user.mapper.UserMapper;
import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.security.Jwt.JwtProvider;
import com.korit.florographyapi.service.LinkTokenService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /// Mappers
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;
    private final LinkTokenService linkTokenService;

    private static Long get64MostSignificantBitsForVersion1() {
        final long currentTimeMillis = System.currentTimeMillis();
        final long time_low = (currentTimeMillis & 0x0000_0000_FFFF_FFFFL) << 32;
        final long time_mid = ((currentTimeMillis >> 32) & 0xFFFF) << 16;
        final long version = 1 << 12;
        final long time_hi = ((currentTimeMillis >> 48) & 0x0FFF);
        return time_low | time_mid | version | time_hi;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Insert Start");
        OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = auth2User.getAttributes();

        // ★ 세션에서 link_token 확인 → 연동 모드인지 판별
        String linkTokenValue = null;
        HttpSession session = request.getSession(false);
        System.out.println("세션 유무 확인: " + session);
        if (session != null) {
            linkTokenValue = (String) session.getAttribute(CustomAuthorizationRequestResolver.LINK_TOKEN_SESSION_KEY);
            // 사용 후 즉시 제거
            session.removeAttribute(CustomAuthorizationRequestResolver.LINK_TOKEN_SESSION_KEY);
        }

        if (linkTokenValue != null) {
            // ===== 연동 모드 =====
            handleAccountLinking(request, response, attributes, linkTokenValue);
        } else {
            // ===== 기존 로그인 모드 =====
            handleLogin(request, response, attributes);
        }
    }

    /**
     * 소셜 계정 연동 처리
     */
    private void handleAccountLinking(HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> attributes, String linkTokenValue) throws IOException {

        LinkToken linkToken = linkTokenService.validateAndConsume(linkTokenValue);

        if (linkToken == null) {
            // 토큰이 만료되었거나 유효하지 않음
            String errorTarget = UriComponentsBuilder.fromUriString("http://localhost:5173/mypage")
                    .queryParam("link", "error")
                    .queryParam("reason", "invalid_token")
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, errorTarget);
            return;
        }

        String providerId = (String) attributes.get("providerId");
        String provider = (String) attributes.get("provider");

        // 이미 다른 계정에 연동된 providerId인지 확인
        ProviderUser existing = userMapper.selectByProviderId(providerId);
        if (existing != null) {
            String errorTarget = UriComponentsBuilder.fromUriString("http://localhost:5173/mypage")
                    .queryParam("link", "error")
                    .queryParam("reason", "already_linked")
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, errorTarget);
            return;
        }

        // 같은 provider로 이미 연동되어 있는지 확인
        ProviderUser duplicateProvider = userMapper.selectByProviderAndUid(provider, linkToken.getUid());
        if (duplicateProvider != null) {
            String errorTarget = UriComponentsBuilder.fromUriString("http://localhost:5173/mypage")
                    .queryParam("link", "error")
                    .queryParam("reason", "provider_already_linked")
                    .build().toUriString();
            getRedirectStrategy().sendRedirect(request, response, errorTarget);
            return;
        }

        // 새 provider_user를 기존 uid에 추가
        ProviderUser newProvider = ProviderUser.builder()
                .uid(linkToken.getUid())
                .email((String) attributes.get("email"))
                .provider(provider)
                .providerId(providerId)
                .createdAt(LocalDate.now())
                .build();
        userMapper.insertProviderUser(newProvider);

        System.out.println("소셜 계정 연동 완료: provider=" + provider + ", uid=" + linkToken.getUid());

        String successTarget = UriComponentsBuilder.fromUriString("http://localhost:5173/mypage")
                .queryParam("link", "success")
                .queryParam("provider", provider)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, successTarget);
    }

    /**
     * 기존 로그인 처리 (변경 없음)
     */
    private void handleLogin(HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> attributes) throws IOException {

        // 게정 생성 여부 체크
        System.out.println("유저 판별 여부:" + (String) attributes.get("email"));
        ProviderUser foundUser = userMapper.selectByProviderId((String) attributes.get("providerId"));
        System.out.println("유저 존재 여부:" + foundUser);
        User user;

        if (foundUser == null) {
            foundUser = ProviderUser.builder()
                    .uid(get64MostSignificantBitsForVersion1().toString())
                    .email((String) attributes.get("email"))
                    .provider((String) attributes.get("provider"))
                    .providerId((String) attributes.get("providerId"))
                    .createdAt(LocalDate.now()).build();

            // 생성하는 Mapper 메서드 삽입
            userMapper.insertProviderUser(foundUser);

            // 그 전에 새로운 계정을 생성할 것인지 여부부터 체크해야함.
            // 일단 그건 명세서에 없으니 뺀 상태로 진행
            user = User
                    .builder()
                    .uid(foundUser.getUid())
                    .nickname((String) attributes.get("nickname"))
                    .email((String) attributes.get("email"))
                    .profileImage((String) attributes.get("profileImage"))
                    .createdAt(LocalDateTime.now())
                    .daysConnected(0l)
                    .build();

            // 유저 생성
            userMapper.insertUser(user);

            // 새로운 유저 생성 완료
            System.out.println("새로운 유저 생성 완료: " + user.toString());
        } else {
            System.out.println(foundUser.getUid());
            user = userMapper.selectByUserId(foundUser.getUid());
            System.out.println("유저 발견!: " + user);
        }

        String target = UriComponentsBuilder.fromUriString("http://localhost:5173/auth/oauth2/callback")
                .queryParam("accessToken", jwtProvider.createToken(String.valueOf(user.getId())))
                .build().toUriString();
//        String target = UriComponentsBuilder.fromUriString("http://localhost:8080/swagger-ui/index-html")
//                .queryParam("accessToken", jwtProvider.createToken(String.valueOf(user.getId())))
//                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, target);
    }
}
