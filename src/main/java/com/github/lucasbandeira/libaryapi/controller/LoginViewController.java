package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    @ResponseBody
    public String homePage( Authentication authentication ) {
        if (authentication instanceof CustomAuthentication customAuthentication)
            System.out.println(customAuthentication.getUsername());

        return "Hello " + authentication.getName();
    }
}
