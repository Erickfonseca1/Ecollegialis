package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;

@Controller
@RequestMapping("/home")
public class HomeController {

  @Autowired
  private AlunoService alunoService;

  //método para redirecionar a view home para chamar o /id
  @GetMapping
  public ModelAndView home() {
    List<Aluno> alunos = alunoService.getAlunos();
    Aluno aluno = alunos.get((int) (Math.random() * alunos.size()));
    return new ModelAndView("redirect:/home/" + aluno.getId());
  }

  @GetMapping("/{id}")
  public ModelAndView home(ModelAndView model, @PathVariable Long id) {
    Aluno aluno = alunoService.getAlunoPorId(id);
    model.addObject("aluno", aluno);
    model.setViewName("home");
    return model;
  }
}
