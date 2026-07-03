package com.korit.florographyapi.config;

import com.korit.florographyapi.security.CustomAuthorizationRequestResolver;
import com.korit.florographyapi.security.CustomOAuth2UserService;
import com.korit.florographyapi.security.Jwt.JwtFilter;
import com.korit.florographyapi.security.OAuth2LoginSuccessHandler;
import com.korit.florographyapi.security.RestAuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final RestAuthEntryPoint restAuthEntryPoint;
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> Customizer.withDefaults());
        http.sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.exceptionHandling(ex -> ex
                .defaultAuthenticationEntryPointFor(
                        restAuthEntryPoint,
                        request -> request.getRequestURI().startsWith("/api")
                )
        );
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.oauth2Login(oauth2 -> oauth2
                .authorizationEndpoint(auth -> auth
                        .authorizationRequestResolver(
                                new CustomAuthorizationRequestResolver(clientRegistrationRepository)
                        )
                )
                .userInfoEndpoint(ui -> ui.userService(customOAuth2UserService))
                .successHandler(oAuth2LoginSuccessHandler)
        );

        http.authorizeHttpRequests(req -> {
            req.requestMatchers("/", "/login", "/login/**", "/oauth2/**").permitAll();
            req.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/api/auth/**").permitAll();
            req.requestMatchers("/api/**").permitAll(); // shareboard 토큰없이 확인하기 위해 넣은것임
            req.anyRequest().authenticated();
        });

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:5174",
                "http://localhost:5175",
                "http://localhost:3000"
        ));
        corsConfiguration.setAllowedMethods(List.of("POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS"));
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Dev-Token"));
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
