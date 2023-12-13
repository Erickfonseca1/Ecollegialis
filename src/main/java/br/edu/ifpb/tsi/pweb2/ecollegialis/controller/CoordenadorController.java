package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import org.springframework.web.servlet.mvc.support.*;

import java.util.List;

@Controller
@RequestMapping("/coordenadores")
public class CoordenadorController {
    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    

    @ModelAttribute("cursos")
    public List<Curso> getCursos() {
        return this.cursoService.getCursos();
    }

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessores();
    }
    
    @GetMapping
    public ModelAndView listCoordenadores(ModelAndView model){
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.setViewName("Coordenador/listaCoordenadores");
        return model;
    }

    @GetMapping("professores/{cursoId}")
    @ResponseBody
    public List<Professor> getProfessorPorCurso(@PathVariable Long cursoId) {
        List<Professor> professores = professorService.getProfessoresPorCurso(cursoId);
        return professores;
    }

    @GetMapping("criar")
    public ModelAndView createCoordenador(ModelAndView model, RedirectAttributes redirectAttributes ){
        model.addObject("coordenador", new Coordenador(new Professor(), new Curso()));
        model.addObject("acao", "salvar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveCoordenador(
            @Valid Coordenador coordenador,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Coordenador/formCoordenador");
            model.addObject("acao", "salvar");
            return model;
        }
        coordenadorService.salvarCoordenador(coordenador);
        model.addObject("coordenadores", professorService.getProfessores());
        model.setViewName("redirect:/coordenadores");
        redirectAttributes.addFlashAttribute("mensagem", "Coordenador Criado com Sucesso");
        redirectAttributes.addFlashAttribute("coordenadoresSalvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editCoordenador(@PathVariable("id") long id, ModelAndView model){
        model.addObject("coordenador", coordenadorService.getCoordenadorPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateCoordenador(
            @Valid Coordenador coordenador,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.addObject("coordenador", coordenadorService.getCoordenadorPorId(id));
            model.setViewName("redirect:/professores/"+id);
            return model;
        }
        coordenadorService.salvarCoordenador(coordenador);
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.setViewName("redirect:/coordenadores");
        redirectAttributes.addFlashAttribute("mensagem", "Coordenador Editado com Sucesso");
        redirectAttributes.addFlashAttribute("coordenadoresEditado", true);
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deleteCoordenador(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        coordenadorService.deletarCoordenador(id);
        model.addObject("coordenadores", coordenadorService.getCoordenadores());
        model.addObject("coordenador", new Coordenador(new Professor(), new Curso()));
        model.setViewName("redirect:/coordenadores");
        redirectAttributes.addFlashAttribute("mensagem","Coordenador Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("coordenadoresDeletado", true);
        return model;
    }
}