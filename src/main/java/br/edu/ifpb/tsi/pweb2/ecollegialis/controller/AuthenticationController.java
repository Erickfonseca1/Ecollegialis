package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {
    @GetMapping("/login")
    public ModelAndView login(ModelAndView model) {
        model.setViewName("Login/LoginScreen");
        return model;
    }
}
