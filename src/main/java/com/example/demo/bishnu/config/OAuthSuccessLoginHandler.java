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
    String email = userDetails.getAttribute("email").toString();
    String firstName = userDetails.getAttribute("given_name").toString();
    String lastName = userDetails.getAttribute("family_name").toString();
    String picture = userDetails.getAttribute("picture").toString();

    BishnuEntity user =  new BishnuEntity();
    BishnuEntity bishnuEntity = userServices.getByEmail(email);
    
    // if user is not exit
    if (bishnuEntity == null) {
      user.setEmail(email);
      user.setFirstName(firstName);
      user.setLastName(lastName);
      user.setRole("NORMAL");
      user.setImage(picture);
      request.getSession().setAttribute("bishnuDto", user);
      super.setDefaultTargetUrl("/bishnu/otpSend");
      super.onAuthenticationSuccess(request, response, authentication);
    } else {
      super.setDefaultTargetUrl("/bishnu/user/dologin");
      super.onAuthenticationSuccess(request, response, authentication);
    }


    
}
 
}
