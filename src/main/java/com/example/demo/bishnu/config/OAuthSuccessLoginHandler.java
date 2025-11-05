package com.example.demo.bishnu.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.example.demo.bishnu.dto.BishnuDto;
import com.example.demo.bishnu.entity.BishnuEntity;

@Component
@SessionAttributes(value = "bishnuDto", types = BishnuDto.class)
public class OAuthSuccessLoginHandler extends  SimpleUrlAuthenticationSuccessHandler {
  
  Logger logger = LoggerFactory.getLogger(OAuthSuccessLoginHandler.class);
  
  @Autowired
  private UserServices userServices;
  
  //@Autowired
  //private ImageCloudinaryService imageCloudinaryService;
  
  
  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {

    DefaultOAuth2User userDetails =  (DefaultOAuth2User) authentication.getPrincipal();
    String email = userDetails.getAttribute("email");

    // User validation is done in CustomOAuth2UserService
    // If we reach here, user exists in database with proper role
    logger.info("Google OAuth login successful for user: " + email);

    super.setDefaultTargetUrl("/bishnu/user/dologin");
    super.onAuthenticationSuccess(request, response, authentication);
}
 
}
