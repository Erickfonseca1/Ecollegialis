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
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.CoordenadorService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;

@Controller
@RequestMapping("/home")
public class HomeController {

  @Autowired
  private AlunoService alunoService;

  @Autowired
  private ProfessorService professorService;

  //redirecionar para o login
  @GetMapping
  public String login() {
    return "seletorTemporario";
  }


  //método para redirecionar a view home para chamar o /id
  @GetMapping("/aluno")
  public ModelAndView homeAluno() {
    List<Aluno> alunos = alunoService.getAlunos();

    if (alunos == null || alunos.isEmpty()) {
      return new ModelAndView("redirect:/");
    } else {
      Aluno aluno = alunos.get((int) (Math.random() * alunos.size()));
      return new ModelAndView("redirect:/home/aluno/" + aluno.getId());
    }
  }

  @GetMapping("/aluno/{id}")
  public ModelAndView homeAluno(ModelAndView model, @PathVariable Long id) {
    Aluno aluno = alunoService.getAlunoPorId(id);
    model.addObject("aluno", aluno);
    model.setViewName("home");
    return model;
  }

  @GetMapping("/professor")
  public ModelAndView homeProfessor() {
    List<Professor> professores = professorService.getProfessores();

    if (professores == null || professores.isEmpty()) {
      return new ModelAndView("redirect:/");
    } else {
      Professor professor = professores.get((int) (Math.random() * professores.size()));
      return new ModelAndView("redirect:/home/professor/" + professor.getId());
    }
  }

  @GetMapping("/professor/{id}")
  public ModelAndView homeProfessor(ModelAndView model, @PathVariable Long id) {
    Professor professor = professorService.getProfessorPorId(id);
    model.addObject("professor", professor);
    model.setViewName("home");
    return model;
  }
}
