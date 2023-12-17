package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AdminService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    
    @GetMapping("/home")
    public ModelAndView home(ModelAndView model) {
        model.setViewName("home");
        model.addObject("titulo", "Administrador");
        model.addObject("texto", "O administrador é responsável por gerenciar diversos itens do sistema, como cursos, professores e coordenadores, dentre outros atributos essenciais.");
        return model;
    }

    @GetMapping("/cursos")
    public ModelAndView listCursos(ModelAndView model) {
        model.addObject("cursos", adminService.getCursos());
        model.addObject("curso", new Curso());
        model.setViewName("Cursos/listaCursos");
        return model;
    }

    @GetMapping("/cursos/criar")
    public ModelAndView criarCurso(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("curso", new Curso());
        model.addObject("acao", "salvar");
        model.setViewName("Cursos/formCurso");
        return model;
    }

    @PostMapping("/cursos/criar")
    public ModelAndView saveCurso(
            @Valid Curso curso,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Cursos/formCurso");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarCurso(curso);
        model.setViewName("redirect:/admin/cursos");
        return model;
    }

    @PostMapping("/cursos/deletar/{id}")
    public ModelAndView deletarCurso(@PathVariable(value = "id") Long id, ModelAndView model) {
        adminService.removerCurso(id);
        model.setViewName("redirect:/admin/cursos");
        return model;
    }


}
