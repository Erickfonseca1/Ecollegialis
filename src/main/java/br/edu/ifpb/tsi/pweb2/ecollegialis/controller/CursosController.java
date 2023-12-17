package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// Será deletado pois esse crud está sendo feito no AdminController
@Controller
@RequestMapping("/cursos")
public class CursosController {
    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ModelAndView listCursos(ModelAndView model) {
        model.addObject("cursos", cursoService.getCursos());
        model.addObject("curso", new Curso());
        model.setViewName("Cursos/listaCursos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createCurso(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("curso", new Curso());
        model.addObject("acao", "salvar");
        model.setViewName("Cursos/formCurso");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveCurso(
            @Valid Curso curso,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Cursos/formCurso");
            model.addObject("acao", "salvar");
            return model;
        }
        cursoService.salvarCurso(curso);
        model.addObject("cursos", cursoService.getCursos());
        model.setViewName("redirect:/cursos");
        redirectAttributes.addFlashAttribute("mensagem", "Curso Criado com Sucesso");
        redirectAttributes.addFlashAttribute("cursoSalvo", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateCurso(
            @Valid Curso curso,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.addObject("curso", cursoService.getCursoPorId(id));
            model.setViewName("redirect:/assuntos/"+id);
            return model;
        }
        cursoService.salvarCurso(curso);
        model.addObject("assuntos", cursoService.getCursos());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Curso Editado com Sucesso");
        redirectAttributes.addFlashAttribute("cursosEditado", true);
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deleteCurso(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        cursoService.deletarCurso(id);
        model.addObject("cursos", cursoService.getCursos());
        model.setViewName("redirect:/cursos");

        redirectAttributes.addFlashAttribute("mensagem", "Curso Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("cursosDeletado", true);

        return model;
    }
}
