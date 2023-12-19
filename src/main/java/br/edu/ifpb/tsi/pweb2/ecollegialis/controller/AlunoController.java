package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AdminService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;
    
    @GetMapping("/home")
    public ModelAndView home(ModelAndView model, Principal principal, HttpSession session){
        Aluno aluno = alunoService.getAlunoPorMatricula(principal.getName());

        // pegar o nome do aluno completo e enviar apenas o primeiro nome para a view
        String[] nomeCompleto = aluno.getNome().split(" ");
        String nome = nomeCompleto[0];
        session.setAttribute("nome", nome);
        session.setAttribute("nomeCompleto", aluno.getNome());
        model.setViewName("home");
        model.addObject("titulo", "Aluno");
        model.addObject("texto", "O aluno pode criar e visualizar os seus processos, além de participar de colegiados que ele faz parte.");
        return model;
    }
}
