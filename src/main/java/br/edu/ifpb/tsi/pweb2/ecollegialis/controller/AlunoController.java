package br.edu.ifpb.pweb2.ecollegialis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlunoController {
    
    @RequestMapping("/aluno")
    public String showHomepage() {
        return "formAluno";
    }
}
