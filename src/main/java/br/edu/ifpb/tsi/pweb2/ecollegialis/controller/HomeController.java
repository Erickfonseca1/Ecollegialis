package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.service.UserService;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

  private UserService userService;

  public HomeController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ModelAndView showHome(Principal principal, ModelAndView model) {
    model.setViewName("redirect:" + userService.userHome(principal));
    return model;
  }
}
