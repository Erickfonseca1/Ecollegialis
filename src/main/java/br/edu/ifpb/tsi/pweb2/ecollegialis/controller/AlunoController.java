package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/aluno")
public class AlunoController {
    
    @GetMapping("/home")
    public ModelAndView home(ModelAndView model){
        model.setViewName("home");
        model.addObject("titulo", "Aluno");
        model.addObject("texto", "O aluno pode criar e visualizar os seus processos, além de participar de colegiados que ele faz parte.");
        return model;
    }
}
