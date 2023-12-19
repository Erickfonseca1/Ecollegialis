package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/aluno/{id}/processos")
public class ProcessoAlunoController {
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private AssuntoService assuntoService;


    @GetMapping
    public ModelAndView listarProcessos(ModelAndView model, @PathVariable("id")Long id){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("processos", processoService.getProcessosPorAluno(aluno));
        model.setViewName("Processo/ListaProcessos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarProcesso(ModelAndView model,@PathVariable("id")Long id){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("processo", new Processo(aluno,new Assunto()));
        model.addObject("assuntos", this.assuntoService.getAssuntos());
        model.setViewName("Processo/formProcesso");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarProcessos(
        @Valid Processo processo,
        BindingResult validation, 
        @PathVariable("id")Long id,
        ModelAndView model
        ){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        if (validation.hasErrors()) {
            model.addObject("aluno", aluno);
            model.addObject("processo", new Processo(aluno,new Assunto()));
            model.setViewName("Processo/formProcesso");
            return model;
        }    
        processo.setAluno(aluno);    
        processoService.salvarProcesso(processo);
        model.addObject("aluno", aluno);
        model.addObject("processos", processoService.getProcessosPorAluno(aluno));
        model.setViewName("redirect:/aluno/"+id+"/processos");
        return model;
    }

}
