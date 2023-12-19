package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;

@Controller
@RequestMapping("/professor/{id}")
public class ProcessoProfessorController {

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ReuniaoService reuniaoService;

    @ModelAttribute("professor")
    public Professor getProfessor(@PathVariable("id") Long id){
        return this.professorService.getProfessorPorId(id);
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
    @GetMapping("/processos")
    public ModelAndView listarProcessoProfessores(ModelAndView model,@PathVariable("id") Long id){
        Professor professor = this.professorService.getProfessorPorId(id);
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
    public ModelAndView atualizarProcessos(ModelAndView model, Processo processo, @PathVariable("id") Long id, @PathVariable("idProcesso") Long idProcesso){
        processoService.atualizarProcesso(processo,idProcesso);
        Professor professor = this.professorService.getProfessorPorId(id);
        model.addObject("processos", processoService.getProcessosPorProfessor(professor));
        model.setViewName("redirect:Professor/"+id+"/processos");
        return model;
    }

    //----- REUNIÕES -----
    @GetMapping("/reunioes")
    public ModelAndView listarReunioesProfessor(ModelAndView model,@PathVariable("id") Long id){
        Professor professor = professorService.getProfessorPorId(id);
        if (professor.getListaColegiados() != null && !professor.getListaColegiados().isEmpty()) {
            Colegiado colegiado = professor.getListaColegiados().get(0);
            List<Reuniao> reunioes = colegiado.getReunioes();
            model.addObject("reuniao", reunioes);
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
