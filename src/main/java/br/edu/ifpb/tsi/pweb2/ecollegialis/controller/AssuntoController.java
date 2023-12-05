package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/assunto")
public class AssuntoController {

    private final AssuntoService assuntoService;

    @Autowired
    public AssuntoController(AssuntoService assuntoService) {
        this.assuntoService = assuntoService;
    }

    @RequestMapping("/form-assunto")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("formAssunto");
        mv.addObject("assunto", new Assunto());
        return mv;
    }

    @PostMapping("/salvar-assunto")
    public String salvarAssunto(@Valid Assunto assunto, BindingResult result) {
        if (result.hasErrors()) {
            return "formAssunto";
        }

        assuntoService.save(assunto);
        return "redirect:/assunto/form-assunto";
    }

    @GetMapping("/lista-assuntos")
    public ModelAndView listaAssuntos() {
        List<Assunto> assuntos = assuntoService.findAll();
        ModelAndView modelAndView = new ModelAndView("listaAssuntos");
        modelAndView.addObject("assuntos", assuntos);
        return modelAndView;
    }

    @RequestMapping("/{id}/delete")
    public ModelAndView deletarAssunto(@PathVariable(value = "id") Long id, ModelAndView mv, RedirectAttributes attr) {
        assuntoService.deleteById(id);
        attr.addFlashAttribute("mensagem", "Assunto removido com sucesso!");
        mv.setViewName("redirect:/assunto/lista-assuntos");
        return mv;
    }

}