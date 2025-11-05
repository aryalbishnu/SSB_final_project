package com.example.demo.bishnu.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomOAuth2FailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2FailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        logger.error("OAuth2 authentication failed: " + exception.getMessage());

        // Check if it's our custom user_not_found error
        if (exception instanceof OAuth2AuthenticationException) {
            OAuth2AuthenticationException oauth2Exception = (OAuth2AuthenticationException) exception;
            if ("user_not_found".equals(oauth2Exception.getError().getErrorCode())) {
                // Redirect to login page with custom error parameter
                getRedirectStrategy().sendRedirect(request, response, "/bishnu/loginForm?oauth_error=not_registered");
                return;
            }
        }

        // For other OAuth2 errors, redirect with generic error
        getRedirectStrategy().sendRedirect(request, response, "/bishnu/loginForm?error");
    }
}
