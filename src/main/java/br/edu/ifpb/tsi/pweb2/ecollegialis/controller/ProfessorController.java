package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ModelAndView listProfessores(ModelAndView model){
        model.addObject("professores", professorService.getProfessores());
        model.addObject("professor", new Professor());
        model.setViewName("Professor/listaProfessores");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createProfessor(ModelAndView model, RedirectAttributes redirectAttributes ){
        model.addObject("professor", new Professor());
        model.addObject("cursos", this.cursoService.getCursos());
        model.addObject("acao", "salvar");
        model.setViewName("Professor/formProfessor");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveProfessor(
            @Valid Professor professor,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Professor/formProfessor");
            model.addObject("acao", "salvar");
            return model;
        }
        professorService.salvarProfessor(professor);
        model.addObject("professores", professorService.getProfessores());
        model.setViewName("redirect:/professores");
        redirectAttributes.addFlashAttribute("mensagem", "Professor Criado com Sucesso");
        redirectAttributes.addFlashAttribute("professoresSalvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editProfessor(@PathVariable("id") long id, ModelAndView model, RedirectAttributes redirectAttributes){
        model.addObject("professor", professorService.getProfessorPorId(id));
        model.addObject("acao", "editar");
        model.addObject("cursos", this.cursoService.getCursos());
        model.setViewName("Professor/formProfessor");
        redirectAttributes.addFlashAttribute("mensagem","Professor Editado com Sucesso");
        redirectAttributes.addFlashAttribute("professoresEditado", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateProfessor(
            @Valid Professor professor,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.addObject("professor", professorService.getProfessorPorId(id));
            model.setViewName("redirect:/professores/"+id);
            return model;
        }
        professorService.salvarProfessor(professor);
        model.addObject("professores", professorService.getProfessores());
        model.setViewName("redirect:/professores");
        redirectAttributes.addFlashAttribute("mensagem", "Professor Editado com Sucesso");
        redirectAttributes.addFlashAttribute("professorEditado", true);
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deleteProfessor(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        professorService.deletarProfessor(id);
        model.addObject("professores", professorService.getProfessores());
        model.addObject("professor", new Professor());
        model.setViewName("redirect:/professores");
        redirectAttributes.addFlashAttribute("mensagem","Professor Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("professoresDeletado", true);
        return model;
    }
}
