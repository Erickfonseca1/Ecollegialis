package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/alunos")
public class AlunosController {
  
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AdminService cursoService;

    @GetMapping
    public ModelAndView listarAlunos(ModelAndView model){
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("Aluno/listaAlunos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarAluno(ModelAndView model, RedirectAttributes redirectAttributes ){
        model.addObject("aluno", new Aluno());
        model.addObject("cursos", this.cursoService.getCursos());
        model.addObject("acao", "salvar");
        model.setViewName("Aluno/formAluno");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarAluno(
        @Valid Aluno aluno,
        BindingResult validation, 
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.setViewName("Aluno/formAluno");
            return model;
        }    
        alunoService.salvarAluno(aluno);
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("redirect:/alunos");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarAluno(@PathVariable("id")Long id, ModelAndView model){
        model.addObject("aluno", alunoService.getAlunoPorId(id));
        model.addObject("cursos", this.cursoService.getCursos());
        model.addObject("acao", "editar");
        model.setViewName("Aluno/formAluno");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView atualizarAluno(
        @Valid Aluno aluno, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.addObject("aluno", alunoService.getAlunoPorId(id));
            model.setViewName("redirect:/alunos/"+id);
            return model;
        }
        alunoService.salvarAluno(aluno);
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("redirect:/alunos");
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deletarAluno(ModelAndView model,@PathVariable("id") Long id){
        this.alunoService.apagarAluno(id);
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("redirect:/alunos");
        return model;
    }
}
