package com.example.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.support.SessionStatus;

import com.example.demo.bishnu.dto.BishnuDto;
import com.example.demo.bishnu.model.ChooseCard;

@Controller
@RequestMapping("")
public class HomeController {
  
  @Autowired
  private ModelMapper modelMapper;

	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "SSB_home_page");
		return "home";

	}

	@GetMapping("/abouts")
	public String abouts(Model model) {
		model.addAttribute("title", "SSB_about_page");
		return "abouts";

	}

	@GetMapping("/services")
	public String services(Model model) {
		model.addAttribute("title", "SSB_sercices_page");
		return "services";
	}

//	@GetMapping("/chooseUser")
//	public String chooseUseer(Model model, HttpSession session, SessionStatus status) {
//		model.addAttribute("title", "SSB_User_Choosee");
//		status.setComplete();
//    session.removeAttribute("bishnuDto");
//		return "login/chooseUser";
//	}
	
	@GetMapping("/chooseCard")
  public String cardChoose(ChooseCard chooseCard, BishnuDto bishnuDto, HttpServletRequest request, Model model, HttpSession session, SessionStatus status) {
      model.addAttribute("title", "SSB_Card_Choose");
      status.setComplete();
      session.removeAttribute("bishnuDto");
      this.modelMapper.map(bishnuDto, chooseCard);
      return "login/chooseCard";  
    }

	@GetMapping("/gmail")
	public String sengemail(Model model) {
		model.addAttribute("title", "SSB_client_support_email_page");
		return "gmail";
	}

}