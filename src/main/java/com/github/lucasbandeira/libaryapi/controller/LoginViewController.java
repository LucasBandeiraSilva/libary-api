package com.github.lucasbandeira.libaryapi.controller;

import com.github.lucasbandeira.libaryapi.security.CustomAuthentication;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Tag(name = "LoginView")
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

    @GetMapping("/authorized")
    @ResponseBody
    public String getAuthorizationCode(@RequestParam("code")String code){
        return " your authorization code is: " + code;
    }
}
