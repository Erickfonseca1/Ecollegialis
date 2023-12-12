package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
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
@RequestMapping("/assuntos")
public class AssuntoController {
    @Autowired
    private AssuntoService assuntoService;

    @GetMapping
    public ModelAndView listAssuntos(ModelAndView model) {
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("Assunto/listaAssuntos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createAssunto(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("assunto", new Assunto());
        model.addObject("acao", "salvar");
        model.setViewName("Assunto/formAssunto");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveAssunto(@Valid Assunto assunto, BindingResult validation,
                                    ModelAndView model,
                                    RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            model.setViewName("Assunto/formAssunto");
            model.addObject("acao", "salvar");
            return model;
        }
        assuntoService.salvarAssunto(assunto);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "O Assunto foi criado!");
        redirectAttributes.addFlashAttribute("O Assunto foi salvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView updateAssunto(@Valid Assunto assunto,
                                      BindingResult validation,
                                      @PathVariable("id") Long id,
                                      ModelAndView model,
                                      RedirectAttributes redirectAttributes){
        if (validation.hasErrors()) {
            model.addObject("assunto", assuntoService.getAssuntoPorId(id));
            model.setViewName("Assunto/formAssunto"+id);
            return model;
        }
        assuntoService.salvarAssunto(assunto);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Assunto Editado com Sucesso");
        redirectAttributes.addFlashAttribute("O Assunto foi Editado", true);
        return model;
    }

    @RequestMapping("{id}/delete")
    public ModelAndView deleteAssunto(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        assuntoService.deletarAssunto(id);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.addObject("assunto", new Assunto());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Assunto Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("O Assunto foi Editado", true);
        return model;
    }
}
