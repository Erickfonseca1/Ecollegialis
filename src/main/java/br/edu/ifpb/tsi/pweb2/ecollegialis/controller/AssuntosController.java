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

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/assuntos")
public class AssuntosController {
    @Autowired
    private AssuntoService assuntoService;

    @GetMapping
    public ModelAndView listarAssuntos(ModelAndView model){
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.addObject("assunto", new Assunto());
        model.setViewName("Assunto/listaAssuntos");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView criarAssuntos(ModelAndView model, RedirectAttributes redirectAttributes ){
        model.addObject("assunto", new Assunto());
        model.addObject("acao", "salvar");
        model.setViewName("Assunto/formAssunto");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView salvarAssuntos(
        @Valid Assunto assunto,
        BindingResult validation, 
        ModelAndView model, 
        RedirectAttributes redirectAttributes
        ){
        if (validation.hasErrors()) {
            model.setViewName("Assunto/formAssunto");
            model.addObject("acao", "salvar");
            return model;
        }    
        assuntoService.salvarAssunto(assunto);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Assunto Criado com Sucesso");
        redirectAttributes.addFlashAttribute("assuntosSalvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarAssunto(@PathVariable("id") long id, ModelAndView model, RedirectAttributes redirectAttributes){
        model.addObject("assunto", assuntoService.getAssuntoPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Assunto/formAssunto");
        redirectAttributes.addFlashAttribute("mensagem","Assunto Editado com Sucesso");
        redirectAttributes.addFlashAttribute("assuntosEditado", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView atualizarAssuntos(
        @Valid Assunto assunto, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model, 
        RedirectAttributes redirectAttributes
        ){
        if (validation.hasErrors()) {
            model.addObject("assunto", assuntoService.getAssuntoPorId(id));
            model.setViewName("redirect:/assuntos/"+id);
            return model;
        }
        assuntoService.salvarAssunto(assunto);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Assunto Editado com Sucesso");
        redirectAttributes.addFlashAttribute("assuntosEditado", true);
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deletarAssuntos(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        assuntoService.deletarAssunto(id);
        model.addObject("assuntos", assuntoService.getAssuntos());
        model.addObject("assunto", new Assunto());
        model.setViewName("redirect:/assuntos");
        redirectAttributes.addFlashAttribute("mensagem", "Assunto Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("assuntosDeletado", true);
        return model;
    }

    
}
