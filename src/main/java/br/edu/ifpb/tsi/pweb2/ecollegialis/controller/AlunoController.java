package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/salvar-aluno")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "Aluno/formAluno";
        }
        if (aluno.getId() != null) {
            alunoService.update(aluno);
        } else {
            alunoService.save(aluno);
        }

        return "redirect:/aluno/list";
    }

    @GetMapping("/new")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("Aluno/formAluno");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @GetMapping("/list")
    public ModelAndView listaAlunos() {
        List<Aluno> alunos = alunoService.findAll();
        ModelAndView modelAndView = new ModelAndView("Aluno/listaAlunos");
        modelAndView.addObject("alunos", alunos);
        return modelAndView;
    }


}
