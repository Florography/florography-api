package com.korit.florographyapi.security;

import com.korit.florographyapi.User.mapper.UserMapper;
import com.korit.florographyapi.entity.ProviderUser;
import com.korit.florographyapi.entity.User;
import com.korit.florographyapi.security.Jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Provider;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /// Mappers
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        System.out.println("Insert Start");
        OAuth2User auth2User = (OAuth2User) authentication.getPrincipal();


        // 게정 생성 여부 체크
        ProviderUser user = userMapper.selectByProviderId((String) auth2User.getAttributes().get("email"));

        if(user == null){
            Map<String,Object> attributes = auth2User.getAttributes();
            user = ProviderUser.builder()
                    .email((String) attributes.get("email"))
                    .provider((String) attributes.get("provider"))
                    .providerId((String) attributes.get("providerId"))
                    .createdAt(LocalDate.now()).build();

            // 생성하는 Mapper 메서드 삽입
             userMapper.insertProviderUser(user);
//            userMapper.insert(user);
        }


        String target = UriComponentsBuilder.fromUriString("http://localhost:5173/auth/oauth2/callback")
                .queryParam("accessToken", jwtProvider.createToken(String.valueOf(user.getId())))
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, target);

    }
}
