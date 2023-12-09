package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String homeController() {
    return "home";
  }
}
