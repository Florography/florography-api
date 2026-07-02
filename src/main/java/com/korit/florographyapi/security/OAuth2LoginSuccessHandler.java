package com.korit.florographyapi.security;

import com.korit.florographyapi.user.mapper.UserMapper;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    /// Mappers
    private final UserMapper userMapper;
    private final JwtProvider jwtProvider;

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

        // 게정 생성 여부 체크
        System.out.println("유저 판별 여부:" + (String) auth2User.getAttributes().get("email"));
        ProviderUser foundUser = userMapper.selectByProviderId((String) auth2User.getAttributes().get("providerId"));
        System.out.println("유저 존재 여부:" + foundUser);
        Map<String,Object> attributes = auth2User.getAttributes();
        User user;

        if(foundUser == null){
            // 여기서 문제 발생 email을 기존의 것을 가져오는데, 만약 새로운 계정으로 로그인하려고 기존의 것으로 서치하다보니 문제가 생김.
            // 그럼 그냥 고유 UID를 사용해서 유저를 분리해주는 것이 좋아보이는데...
            // 있다가 의논해보는걸로
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
                    .nickname((String)attributes.get("nickname"))
                    .email((String) attributes.get("email"))
                    .profileImage((String)attributes.get("profileImage"))
                    .createdAt(LocalDateTime.now())
                    .daysConnected(0l)
                    .build();

            // 유저 생성
            userMapper.insertUser(user);

            // 새로운 유저 생성 완료
            System.out.println("새로운 유저 생성 완료: " + user.toString());
        }
        else {
            user = userMapper.selectByUserEmail(foundUser.getUid());

            System.out.println("유저 발견!: " + user);
        }

        String target = UriComponentsBuilder.fromUriString("http://localhost:5173/auth/oauth2/callback")
                .queryParam("accessToken", jwtProvider.createToken(String.valueOf(user.getId())))
                .build().toUriString();

        getRedirectStrategy().sendRedirect(request, response, target);

    }
}
