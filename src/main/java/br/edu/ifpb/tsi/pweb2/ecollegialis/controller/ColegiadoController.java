package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/colegiados")
public class ColegiadoController {

    @Autowired
    private ColegiadoService colegiadoService;

    @Autowired
    private ProfessorService professorService;

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessores();
    }

    @GetMapping
    public ModelAndView listColegiados(ModelAndView model){
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("Colegiado/listaColegiados");
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createColegiado(ModelAndView model, RedirectAttributes redirectAttributes ){
        List<Professor> membros = new ArrayList<>();
        for(int i=0 ; i<4;i++){
            membros.add(new Professor());
        }
        model.addObject("colegiado", new Colegiado(membros));
        model.addObject("membros", membros);
        model.addObject("acao", "salvar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveColegiado(
            @Valid Colegiado colegiado,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            for(int i=0 ; i<4;i++){
                membros.add(new Professor());
            }
            model.addObject("membros", membros);
            model.setViewName("Colegiado/formColegiado");
            model.addObject("acao", "salvar");
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem", "Colegiado Criado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosSalvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editColegiado(@PathVariable("id") long id, ModelAndView model, RedirectAttributes redirectAttributes){
        List<Professor> membros = new ArrayList<Professor>();
        for(int i=0 ; i<4;i++){
            membros.add(new Professor());
        }
        model.addObject("membros", membros);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Colegiado/formColegiado");
        redirectAttributes.addFlashAttribute("mensagem","Colegiado Editado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosEditado", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateColegiado(
            @Valid Colegiado colegiado,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            for(int i=0 ; i<4;i++){
                membros.add(new Professor());
            }
            model.addObject("membros", membros);
            model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
            model.setViewName("redirect:/colegiados/"+id);
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem", "Colegiado Editado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosEditado", true);
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deleteColegiado(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        colegiadoService.deletarColegiado(id);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.addObject("colegiado", new Colegiado(new ArrayList<Professor>()));
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem","Colegiado Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadoDeletado", true);
        return model;
    }
}

