package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/professores")
public class ProfessoresController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ModelAndView listarProfessores(ModelAndView model){
        model.addObject("professores", professorService.getProfessores());
        model.addObject("professor", new Professor());
        model.setViewName("Professor/listaProfessores");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarProfessores(ModelAndView model){
        model.addObject("professor", new Professor());
        model.addObject("cursos", this.cursoService.getCursos());
        model.addObject("acao", "salvar");
        model.setViewName("Professor/formProfessor");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarProfessores(
        @Valid Professor professor,
        BindingResult validation, 
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.setViewName("Professor/formProfessor");
            model.addObject("acao", "salvar");
            return model;
        }    
        professorService.salvarProfessor(professor);
        model.addObject("professores", professorService.getProfessores());
        model.setViewName("redirect:/professores");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarProfessores(@PathVariable("id") long id, ModelAndView model){
        model.addObject("professor", professorService.getProfessorPorId(id));
        model.addObject("acao", "editar");
        model.addObject("cursos", this.cursoService.getCursos());
        model.setViewName("Professor/formProfessor");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView atualizarProfessores(
        @Valid Professor professor, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.addObject("professor", professorService.getProfessorPorId(id));
            model.setViewName("redirect:/professores/"+id);
            return model;
        }
        professorService.salvarProfessor(professor);
        model.addObject("professores", professorService.getProfessores());
        model.setViewName("redirect:/professores");
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deletarProfessores(@PathVariable("id") Long id, ModelAndView model){
        professorService.deletarProfessor(id);
        model.addObject("professores", professorService.getProfessores());
        model.addObject("professor", new Professor());
        model.setViewName("redirect:/professores");
        return model;
    }
}
