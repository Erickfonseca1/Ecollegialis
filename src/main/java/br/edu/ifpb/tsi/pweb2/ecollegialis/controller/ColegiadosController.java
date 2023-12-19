package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/colegiados")
public class ColegiadosController {
    
    @Autowired
    private ColegiadoService colegiadoService;

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

    @ModelAttribute("coordenadores")
    public List<Coordenador> getCoordenadores(){
        return this.coordenadorService.getCoordenadores();
    }

    @GetMapping
    public ModelAndView listarColegiados(ModelAndView model){
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("Colegiado/listaColegiados");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarColegiados(ModelAndView model){
        List<Professor> membros = new ArrayList<Professor>();
        model.addObject("colegiado", new Colegiado(membros));
        model.addObject("coordenador", this.coordenadorService.getCoordenadorPorId(1L));
        model.addObject("membros", membros);
        model.addObject("acao", "salvar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarColegiados(
        @Valid Colegiado colegiado,
        BindingResult validation,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            model.addObject("membros", membros);
            model.setViewName("Colegiado/formColegiado");
            model.addObject("acao", "salvar");
            return model;
        }
        colegiado.setCoordenador(this.coordenadorService.getCoordenadorPorCurso(colegiado.getCurso().getId()));
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("redirect:/colegiados");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarColegiados(@PathVariable("id") long id, ModelAndView model){
        List<Professor> membros = new ArrayList<Professor>();
        model.addObject("membros", membros);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView atualizarColegiados(
        @Valid Colegiado colegiado, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            model.addObject("membros", membros);
            model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
            model.setViewName("redirect:/colegiados/"+id);
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.setViewName("redirect:/colegiados");
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deletarColegiados(@PathVariable("id") Long id, ModelAndView model){
        colegiadoService.deletarColegiado(id);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.addObject("colegiado", new Colegiado(new ArrayList<Professor>()));
        model.setViewName("redirect:/colegiados");
        return model;
    }
}
