package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/aluno/{id}/processos")
public class ProcessoController {

    @RequestMapping("/aluno")
    public String showHomePage(){
        return "aluno/home";
    }

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private AssuntoService assuntoService;

    @ModelAttribute("assuntos")
    public List<Assunto> getAssuntos(){
        return this.assuntoService.getAssuntos();
    }


    @GetMapping
    public ModelAndView listProcessos(ModelAndView model, @PathVariable("id")Long id){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("processos", processoService.getProcessosPorAluno(aluno));
        model.setViewName("Processo/ListaProcessos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createProcesso(ModelAndView model,@PathVariable("id")Long id, RedirectAttributes redirectAttributes ){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("processo", new Processo(aluno,new Assunto()));
        model.setViewName("Processo/formProcesso");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveProcesso(
            @Valid Processo processo,
            BindingResult validation,
            @PathVariable("id")Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
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
        redirectAttributes.addFlashAttribute("mensagem", "Processo criado com Sucesso");
        return model;
    }
}
