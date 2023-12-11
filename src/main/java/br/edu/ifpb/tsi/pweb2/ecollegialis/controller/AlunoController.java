package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @GetMapping
    public ModelAndView listAlunos(ModelAndView model){
        model.addObject("aluno", alunoService.getAlunos());
        model.setViewName("Aluno/listaAlunos");
        return model;
    }
    @GetMapping("criar")
    public ModelAndView createAluno(ModelAndView model, RedirectAttributes redirectAttributes ){
        model.addObject("aluno", new Aluno());
        model.addObject("acao", "salvar");
        model.setViewName("Aluno/formAluno");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveAluno(
            @Valid Aluno aluno,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Aluno/formAluno");
            return model;
        }
        alunoService.salvarAluno(aluno);
        model.addObject("aluno", alunoService.getAlunos());
        model.setViewName("redirect:/aluno");
        redirectAttributes.addFlashAttribute("mensagem", "Aluno Criado com Sucesso");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editAluno(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        Aluno aluno = alunoService.getAlunoPorId(id);
        model.addObject("aluno", aluno);
        model.addObject("acao", "editar");
        model.setViewName("Aluno/formAluno");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateAluno(
            @PathVariable("id") Long id,
            @Valid Aluno aluno,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Aluno/formAluno");
            return model;
        }
        alunoService.salvarAluno(aluno);
        model.addObject("aluno", alunoService.getAlunos());
        model.setViewName("redirect:/aluno");
        redirectAttributes.addFlashAttribute("mensagem", "Aluno Atualizado com Sucesso");
        return model;
    }

    @GetMapping("{id}/delete")
    public ModelAndView deleteAluno(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        alunoService.apagarAluno(id);
        model.addObject("aluno", alunoService.getAlunos());
        model.setViewName("redirect:/aluno");
        redirectAttributes.addFlashAttribute("mensagem", "Aluno Deletado com Sucesso");
        return model;
    }

}
