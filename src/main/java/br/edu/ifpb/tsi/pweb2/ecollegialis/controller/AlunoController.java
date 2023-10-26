package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import ch.qos.logback.core.model.Model;
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
import org.w3c.dom.Text;

import java.util.List;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoService alunoService;

    @Autowired
    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @RequestMapping("/aluno")
    public ModelAndView showHomepage(ModelAndView mv) {
        mv.addObject("nome", "FelaDaPuta");
        mv.setViewName("aluno");
        return mv;
    }

    @PostMapping("/salvar-aluno")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result) {
        if (result.hasErrors()) {
            return "formAluno";
        }

        alunoService.save(aluno);
        return "redirect:/aluno/form-aluno";
    }

    @GetMapping("/form-aluno")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("formAluno");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @GetMapping("/lista-alunos")
    public ModelAndView listaAlunos() {
        List<Aluno> alunos = alunoService.findAll();
        ModelAndView modelAndView = new ModelAndView("listaAlunos");
        modelAndView.addObject("alunos", alunoService.findAll());
        return modelAndView;
    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deletarAluno(@PathVariable(value = "id") Long id, ModelAndView mv, RedirectAttributes attr) {
        alunoService.deleteById(id);
        attr.addFlashAttribute("mensagem", "Aluno removido com sucesso!");
        mv.setViewName("redirect:/aluno/lista-alunos");
        return mv;
    }

}
