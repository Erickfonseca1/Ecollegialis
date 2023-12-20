package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/professor")
public class ProfessorController {
    
    @Autowired
    private ProfessorService professorService;

    @GetMapping("/home")
    public ModelAndView home(ModelAndView model, Principal principal, HttpSession session){
        Professor professor = professorService.getProfessorPorMatricula(principal.getName());
        String[] nomeCompleto = professor.getNome().split(" ");
        String nome = nomeCompleto[0];
        session.setAttribute("nome", nome);
        session.setAttribute("nomeCompleto", professor.getNome());
        model.setViewName("home");
        model.addObject("titulo", "Professor");
        model.addObject("texto", "O professor pode criar e visualizar os seus processos, além de participar de colegiados que ele faz parte.");
        return model;
    }
}
