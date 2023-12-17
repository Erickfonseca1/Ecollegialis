package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProcessoProfessorController {

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @ModelAttribute("professor")
    public Professor getProfessor(@PathVariable("id") Long id){
        return this.professorService.getProfessorPorId(id);
    }

    @GetMapping
    public ModelAndView showPainelProcessos(ModelAndView model, @PathVariable("id") Long id){
        Professor professor = this.professorService.getProfessorPorId(id);
        model.addObject("processos", processoService.getProcessosPorProfessor(professor));
        model.setViewName("/professor/painel");
        return model;
    }

    @GetMapping("{idProcesso}")
    public ModelAndView showProcesso(ModelAndView model, @PathVariable("idProcesso") Long idProcesso){
        model.addObject("processo", processoService.getProcessoPorId(idProcesso));
        model.setViewName("/professor/processo");
        return model;
    }
}
