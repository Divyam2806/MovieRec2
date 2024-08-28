package com.example.movierec2.controller;

import com.example.movierec2.dto.RegistrationDto;
import com.example.movierec2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    //show the registration form
    @GetMapping("/register")
    public String register(){
        return "register";
    }

    //register a user based on values of the registration form
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("users") RegistrationDto registrationDto){  //@ModelAttribute binds the data to the DTO
        userService.registerUser(registrationDto.getUsername(), registrationDto.getPassword());
        return "redirect:/login";
    }


}
