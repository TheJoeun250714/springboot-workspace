package edu.the.joeun.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {
    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("loginUser") != null) {
            return "redirect:/";
        }
        return "/login";
    }
}

