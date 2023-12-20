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

    //----- REUNIÕES -----
    @GetMapping("/reunioes")
    public ModelAndView listarReunioesProfessor(
            ModelAndView model,
            @PathVariable("id") Long id,
            @RequestParam(name = "status", required = false) String status) {

        Professor professor = professorService.getProfessorPorId(id);

        if (professor.getListaColegiados() != null && !professor.getListaColegiados().isEmpty()) {
            Colegiado colegiado = professor.getListaColegiados().get(0);

            if (colegiado != null) {
                List<Reuniao> reunioes;

                if ("finalizada".equalsIgnoreCase(status)) {
                    reunioes = reuniaoService.getReunioesFinalizadasDoColegiado(colegiado);
                } else if ("agendada".equalsIgnoreCase(status)) {
                    reunioes = reuniaoService.getReunioesAgendadasDoColegiado(colegiado);
                } else {
                    reunioes = reuniaoService.getReunioes();
                }

                model.addObject("reunioes", reunioes);
            }
        }
        model.setViewName("Professor/painel-reunioes");
        return model;
    }

    @GetMapping("/reunioes/{idReuniao}")
    public ModelAndView listarReunioes(ModelAndView model, @PathVariable("idReuniao") Long idReuniao){
        model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
        model.setViewName("Professor/reuniao");
        return model;
    }
}
