package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/coordenadores")
public class CoordenadoresController {
    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessores();
    }

    @ModelAttribute("cursos")
    public List<Curso> getCursos(){
        return this.cursoService.getCursos();
    }

    @GetMapping
    public ModelAndView listarCoordenadores(ModelAndView model){
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.setViewName("Coordenador/listaCoordenadores");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarCoordenadores(ModelAndView model){
        model.addObject("coordenador", new Coordenador());
        model.addObject("acao", "salvar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarCoordenadores(
        @Valid Coordenador coordenador,
        BindingResult validation, 
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.setViewName("Coordenador/formCoordenador");
            model.addObject("acao", "salvar");
            return model;
        }
        coordenadorService.salvarCoordenador(coordenador);
        model.addObject("coordenadores", professorService.getProfessores());
        model.setViewName("redirect:/coordenadores");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarCoordenadores(@PathVariable("id") long id, ModelAndView model){
        model.addObject("coordenador", coordenadorService.getCoordenadorPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView salvarCoordenadores(
        @Valid Coordenador coordenador, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            model.addObject("coordenador", coordenadorService.getCoordenadorPorId(id));
            model.setViewName("redirect:/professores/"+id);
            return model;
        }
        coordenadorService.salvarCoordenador(coordenador);
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.setViewName("redirect:/coordenadores");
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deletarCoordenadores(@PathVariable("id") Long id, ModelAndView model){
        coordenadorService.deletarCoordenador(id);
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.setViewName("redirect:/coordenadores");
        return model;
    }
}
