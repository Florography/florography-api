package com.korit.florographyapi.security.Jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")) {
            String accessToken = authorization.substring(7);
            try {
                Jws<Claims> claimsJwt = jwtProvider.parseAndValidate(accessToken);
                Long userId = Long.valueOf(claimsJwt.getPayload().getId());

                Authentication authentication = new UsernamePasswordAuthenticationToken(userId, "", List.of(new SimpleGrantedAuthority("ROLE_USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (io.jsonwebtoken.JwtException e) {
                // 토큰이 만료되었거나 유효하지 않은 경우 무시
                // SecurityContext에 인증 정보가 들어가지 않으므로 Spring Security가 알아서 401(Unauthorized) 또는 403 처리를 합니다.
            }
        }
        filterChain.doFilter(request, response);
    }

}


