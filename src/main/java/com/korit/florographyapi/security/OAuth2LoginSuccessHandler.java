package com.korit.florographyapi.security;

import com.korit.florographyapi.User.mapper.UserMapper;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.security.Jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.session.DataSourceStore;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /// Mappers
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();

        // 게정 생성 여부 체크
        User user = userMapper.selectByProvider(auth2User.getName());

        if(user == null){
            Map<String,Object> attributes = auth2User.getAttributes();
            user = User.builder()
                    .email((String)attributes.get("email"))
                    .name((String)attributes.get("nickname"))
                    .profileImage((String)attributes.get("profileImage"))
                    .provider((String)attributes.get("provider"))
                    .providerId((String)attributes.get("providerId"))
                    .createdAt(LocalDateTime.now())
                    .build();
            userMapper.insert(user);
        }

        String target = UriComponentsBuilder.fromUriString("http://localhost:5173/auth/oauth2/callback")
                .queryParam("accessToken", jwtProvider.createToken(String.valueOf(user.getId())))
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, target);

    }
}
