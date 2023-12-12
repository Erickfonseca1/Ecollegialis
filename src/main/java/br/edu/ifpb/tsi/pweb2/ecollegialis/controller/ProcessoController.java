package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/processos")
public class ProcessoController {
    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private AssuntoService assuntoService;

    @GetMapping("/aluno/{id}")
    public ModelAndView listProcessos(ModelAndView model, @PathVariable("id")Long id){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("processos", processoService.getProcessosPorAluno(aluno));
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("Processo/listaProcessos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createProcesso(ModelAndView model,@PathVariable("id")Long id, RedirectAttributes redirectAttributes ){
        Aluno aluno = this.alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.addObject("processo", new Processo(aluno,new Assunto()));
        model.setViewName("Processo/formProcesso");
        return model;
    }

    @PostMapping ("criar")
    public ModelAndView saveProcesso(
            @Valid Processo processo,
            BindingResult validation,
            @PathVariable("id")Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes){
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
        model.setViewName("/processos/"+id+"/aluno");
        redirectAttributes.addFlashAttribute("mensagem", "Processo criado com Sucesso");
        return model;
    }
}
