package com.resolve.Re_Solve.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController { // html 반환 컨트롤러
    @GetMapping("/")
    public String home() {
        return "index"; // templates/index.html
    }
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // templates/login.html
    }

    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // templates/signup.html
    }
}
