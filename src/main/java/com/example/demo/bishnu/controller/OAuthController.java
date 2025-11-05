package com.example.demo.bishnu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.bishnu.config.OAuthSuccessLoginHandler;
import com.example.demo.bishnu.service.ImageCloudinaryService;

@Controller
@RequestMapping("/bishnu")
public class OAuthController {
  
  @Autowired
  private ImageCloudinaryService imageCloudinaryService;
  
  @Autowired
  private OAuthSuccessLoginHandler oAuthSuccessLoginHandler;
  
  @GetMapping("/googleAuth")
  public String otpSendBy(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication, Model model) throws IOException, ServletException {
    model.addAttribute("title", "otp email-send");
    oAuthSuccessLoginHandler.onAuthenticationSuccess(request, response, authentication);
    return "userLogin/forgotPassword";
  }

}
