package com.korit.florographyapi.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

/**
 * OAuth2 인증 요청 시 link_token 파라미터를 감지하여 세션에 저장하는 커스텀 리졸버.
 *
 * OAuth2 플로우 중 임시 세션이 생성되므로, STATELESS 설정에서도
 * 리다이렉트 사이클 동안에는 세션을 활용할 수 있습니다.
 */
public class CustomAuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    public static final String LINK_TOKEN_SESSION_KEY = "LINK_TOKEN";

    private final DefaultOAuth2AuthorizationRequestResolver defaultResolver;

    public CustomAuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository) {
        this.defaultResolver = new DefaultOAuth2AuthorizationRequestResolver(
                clientRegistrationRepository, "/oauth2/authorization"
        );
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request);
        return processLinkToken(request, authorizationRequest);
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        OAuth2AuthorizationRequest authorizationRequest = defaultResolver.resolve(request, clientRegistrationId);
        return processLinkToken(request, authorizationRequest);
    }

    private OAuth2AuthorizationRequest processLinkToken(HttpServletRequest request, OAuth2AuthorizationRequest authorizationRequest) {
        if (authorizationRequest == null) {
            return null;
        }

        String linkToken = request.getParameter("link_token");
        if (linkToken != null && !linkToken.isBlank()) {
            // link_token을 세션에 저장 (OAuth2 리다이렉트 사이클 동안 유지됨)
            HttpSession session = request.getSession(true);
            session.setAttribute(LINK_TOKEN_SESSION_KEY, linkToken);
        }

        return authorizationRequest;
    }
}
