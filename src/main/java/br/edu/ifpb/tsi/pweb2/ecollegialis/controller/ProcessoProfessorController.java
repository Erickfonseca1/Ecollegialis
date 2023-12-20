package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;

@Controller
@RequestMapping("/professor/processos")
@PreAuthorize("hasRole('PROFESSOR')")
public class ProcessoProfessorController {

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ReuniaoService reuniaoService;
    
    @Autowired
    private AdminService adminService;

    @ModelAttribute("professor")
    public Professor getProfessor(Principal principal){
        return this.professorService.getProfessorPorMatricula(principal.getName());
    }

    @ModelAttribute("Deferido")
    public TipoDecisao getDeferido(){
        return TipoDecisao.DEFERIDO;
    }

    @ModelAttribute("Indeferido")
    public TipoDecisao getIndeferido(){
        return TipoDecisao.INDEFERIDO;
    }

    //----- PROCESSOS -----
    @GetMapping
    public ModelAndView listarProcessoProfessores(ModelAndView model,Principal principal){
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        model.addObject("processos", processoService.getProcessosPorProfessor(professor));
        model.setViewName("Professor/listarProcessosProfessor");
        return model;
    }

    @GetMapping("/processos/{idProcesso}")
    public ModelAndView listarProcessos(ModelAndView model, @PathVariable("idProcesso") Long idProcesso){
        model.addObject("processo", processoService.getProcessoPorId(idProcesso));
        model.setViewName("Professor/professorVotaProcesso");
        return model;
    }

    @PostMapping("/processos/{idProcesso}")
    public ModelAndView atualizarProcessos(ModelAndView model, Processo processo, Principal principal, @PathVariable("idProcesso") Long idProcesso){
        processoService.atualizarProcesso(processo,idProcesso);
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        model.addObject("processos", processoService.getProcessosPorProfessor(professor));
        model.setViewName("redirect:professor/processos");
        return model;
    }
}
