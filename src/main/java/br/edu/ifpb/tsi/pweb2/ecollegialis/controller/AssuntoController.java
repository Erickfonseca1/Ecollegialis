package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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
        return "redirect:/assunto/form-assunto"; // Redireciona de volta para o formulário
    }
}
