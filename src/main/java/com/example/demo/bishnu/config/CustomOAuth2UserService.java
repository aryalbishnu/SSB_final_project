package com.example.demo.bishnu.config;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.demo.bishnu.entity.BishnuEntity;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private static final Logger logger = LoggerFactory.getLogger(CustomOAuth2UserService.class);

    @Autowired
    private UserServices userServices;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");

        logger.info("OAuth2 login attempt with email: " + email);

        // Check if user exists in database
        BishnuEntity user = userServices.getByEmail(email);

        if (user != null) {
            // User exists - add proper role authority
            logger.info("User found in database with role: " + user.getRole());
            Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole())
            );

            Map<String, Object> attributes = oAuth2User.getAttributes();

            return new DefaultOAuth2User(authorities, attributes, "email");
        }

        // User doesn't exist - throw exception to trigger failure handler
        logger.warn("User not found in database: " + email);
        OAuth2Error oauth2Error = new OAuth2Error(
            "user_not_found",
            "This Gmail account is not registered. Please sign up first.",
            null
        );
        throw new OAuth2AuthenticationException(oauth2Error, oauth2Error.toString());
    }
}
