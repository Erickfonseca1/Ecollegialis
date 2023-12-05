package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
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

import java.util.List;
@Controller
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorService professorService;

    @Autowired
    public ProfessorController(ProfessorService professorService) {
        this.professorService = professorService;
    }
    @RequestMapping("/form-professor")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("formProfessor");
        mv.addObject("professor", new Professor());
        return mv;
    }

    @PostMapping("/salvar-professor")
    public String salvarProfessor(@Valid Professor professor, BindingResult result) {
        if (result.hasErrors()) {
            return "formProfessor";
        }

        System.out.println(professor.getId());

        if (professor.getId() != null) {
            professorService.update(professor);
        } else {
            professorService.save(professor);
        }

        return "redirect:/professor/lista-professores";
    }

    @GetMapping("/{id}/editar")
    public ModelAndView exibirFormularioEdicao(@PathVariable(value = "id") Long id) {
        Professor professor = professorService.findById(id);
        ModelAndView modelAndView = new ModelAndView("formProfessor");
        modelAndView.addObject("professor", professor);
        return modelAndView;
    }

    @GetMapping("/lista-professores")
    public ModelAndView listaProfessores() {
        List<Professor> professores = professorService.findAll();
        ModelAndView modelAndView = new ModelAndView("listaProfessores");
        modelAndView.addObject("professores", professores);
        return modelAndView;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deletarProfessor(@PathVariable(value = "id") Long id, ModelAndView mv, RedirectAttributes attr) {
        professorService.deleteById(id);
        attr.addFlashAttribute("mensagem", "Professor removido com sucesso!");
        mv.setViewName("redirect:/professor/lista-professores");
        return mv;
    }
}