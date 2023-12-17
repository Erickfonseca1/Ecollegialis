package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.CursoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public ModelAndView listAlunos(ModelAndView model) {
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("Aluno/listaAlunos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createAluno(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("aluno", new Aluno());
        model.addObject("cursos", cursoService.getCursos());
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
    ) {
        try {
            if (validation.hasErrors()) {
                model.setViewName("Aluno/formAluno");
                model.addObject("acao", "salvar");
                return model;
            }
            alunoService.salvarAluno(aluno);
            model.addObject("alunos", alunoService.getAlunos());
            model.setViewName("redirect:/alunos");
            redirectAttributes.addFlashAttribute("successMessage", "Aluno criado com sucesso!");
        } catch (Exception e) {
            model.addObject("errorMessage", "Erro ao criar o aluno. Por favor, tente novamente.");
            model.setViewName("Aluno/formAluno");
        }
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editAluno(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("aluno", alunoService.getAlunoPorId(id));
        model.addObject("cursos", this.cursoService.getCursos());
        model.addObject("acao", "editar");
        model.setViewName("Aluno/formAluno");
        redirectAttributes.addFlashAttribute("mensagem", "O Aluno foi Editado!");
        redirectAttributes.addFlashAttribute("O Aluno foi Editado", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateAluno(
            @Valid Aluno aluno,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            model.addObject("aluno", alunoService.getAlunoPorId(id));
            model.setViewName("Aluno/formAluno"+id);
            return model;
        }
        alunoService.salvarAluno(aluno);
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("redirect:/alunos");
        redirectAttributes.addFlashAttribute("mensagem", "O Aluno foi atualizado!");
        redirectAttributes.addFlashAttribute("O Aluno foi Salvo", true);
        return model;
    }

    @GetMapping("{id}/delete")
    public ModelAndView deleteAluno(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        alunoService.apagarAluno(id);
        model.addObject("alunos", alunoService.getAlunos());
        model.setViewName("redirect:/alunos");
        redirectAttributes.addFlashAttribute("mensagem", "O Aluno foi deletado!");
        redirectAttributes.addFlashAttribute("O Aluno foi deletado!", true);
        return model;
    }

}
