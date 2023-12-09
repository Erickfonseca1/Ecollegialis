package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/professor")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ColegiadoService colegiadoService;

    @Autowired
    private ProcessoService processoService;

    @GetMapping
    public ModelAndView getProfessores(ModelAndView modelAndView) {
        List<Professor> professores = professorService.findAll();

        modelAndView.setViewName("professores/index");
        modelAndView.addObject("professores", professores);


        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView current(ModelAndView modelAndView, Professor professor) {

        List<Colegiado> colegiados = colegiadoService.findAll();

        modelAndView.setViewName("professores/new");
        modelAndView.addObject("professorDTO", professor);
        modelAndView.addObject("colegiados", colegiados);

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deletarProfessor(ModelAndView modelAndView, @PathVariable (value = "id") Long id, RedirectAttributes attr){
        try {
            var professorExistente = professorService.findById(id);
            professorService.deleteById(professorExistente.getId());
            attr.addFlashAttribute("message", "OK: Professor excluído com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/professores");
        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            Professor professor = professorService.findById(id);

            var request = new Professor();

            modelAndView.setViewName("professores/edit");
            modelAndView.addObject("professorId", professor.getId());
            modelAndView.addObject("professor", request);

        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/atribuir")
    public ModelAndView atribuirprocesso(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            Professor professor = professorService.findById(id);
            List<Professor> professores = professorService.findAll();
            List<Processo> processos = processoService.findAll();
            List<Colegiado> colegiados = colegiadoService.findAll();

            var request = new Professor();

            modelAndView.setViewName("professores/atribuirprocesso");
            modelAndView.addObject("professorId", professor.getId());
            modelAndView.addObject("professor", request);
            modelAndView.addObject("professores", professores);
            modelAndView.addObject("processos", processos);
            modelAndView.addObject("colegiados", colegiados);
            modelAndView.addObject("professorNome", professor.getNome());

        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @GetMapping ("{id}/adicionarcolegiado")
    public ModelAndView adicionarcolegiado(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            Professor professor = professorService.findById(id);
            List<Professor> professores = professorService.findAll();
            List<Colegiado> colegiados = colegiadoService.findAll();

            var request = new Professor();

            modelAndView.setViewName("professores/atribuircolegiado");
            modelAndView.addObject("professorId", professor.getId());
            modelAndView.addObject("professor", request);
            modelAndView.addObject("professores", professores);
            modelAndView.addObject("colegiados", colegiados);

        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView atualizarProfessor(ModelAndView modelAndView, @PathVariable Long id, @Valid Professor professor, BindingResult bindingResult, RedirectAttributes attr){

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/professores/{id}/edit");
        } else {

            attr.addFlashAttribute("message", "OK: Professor editado com sucesso!");
            attr.addFlashAttribute("error", "false");
            professorService.update(professor, id);

            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @PostMapping("/{id}/atribuirprocesso")
    public ModelAndView atribuirprocesso(ModelAndView modelAndView, @PathVariable Long id, @Valid Professor professorObj, BindingResult bindingResult, RedirectAttributes attr){

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/professores/{id}/edit");
        } else {

            attr.addFlashAttribute("message", "OK: Professor editado com sucesso!");
            attr.addFlashAttribute("error", "false");
            professorService.update(professorObj, id);

            modelAndView.setViewName("redirect:/professores");
        }

        return modelAndView;
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Professor buscaProfessor(@PathVariable Long id){
        return professorService.findById(id);
    }
}