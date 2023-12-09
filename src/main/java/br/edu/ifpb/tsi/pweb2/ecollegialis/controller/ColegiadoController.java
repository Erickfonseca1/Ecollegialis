package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/colegiados")
public class ColegiadoController {

    @Autowired
    private ColegiadoService colegiadoService;

    @GetMapping
    public ModelAndView getColegiados(ModelAndView modelAndView) {
        List<Colegiado> colegiados = colegiadoService.findAll();

        modelAndView.setViewName("colegiados/index");
        modelAndView.addObject("colegiados", colegiados);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView criarColegiado(ModelAndView modelAndView, Colegiado colegiado) {
        List<Colegiado> colegiados = colegiadoService.findAll();

        modelAndView.setViewName("colegiados/new");
        modelAndView.addObject("colegiados", colegiados);
        modelAndView.addObject("colegiado", colegiado);

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deletarAssunto(ModelAndView modelAndView, @PathVariable(value = "id") Long id, RedirectAttributes attr){
        try {
            colegiadoService.deletarColegiado(id);
            attr.addFlashAttribute("message", "OK: Colegiado excluído com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/colegiados");
        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/colegiados");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            Colegiado colegiado = colegiadoService.findById(id);

            var request = new Colegiado();

            modelAndView.setViewName("colegiados/edit");
            modelAndView.addObject("colegiadoId", colegiado.getId());
            modelAndView.addObject("colegiado", request);

        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/colegiados");
        }

        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView atualizarColegiado(ModelAndView modelAndView, @PathVariable Long id, @Valid Colegiado colegiado, BindingResult bindingResult, RedirectAttributes attr){

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("redirect:/colegiados/{id}/edit");
        } else {

            attr.addFlashAttribute("message", "OK: Colegiado editado com sucesso!");
            attr.addFlashAttribute("error", "false");
            colegiadoService.atualizarColegiado(id, colegiado);

            modelAndView.setViewName("redirect:/colegiados");
        }

        return modelAndView;
    }

    @PostMapping
    public ModelAndView criarColegiado(ModelAndView modelAndView, @Valid Colegiado colegiado, BindingResult bindingResult, RedirectAttributes attr) {

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("colegiados/new");
        } else {
            colegiadoService.cadastrarColegiado(colegiado);
            attr.addFlashAttribute("message", "Colegiado cadastrado com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/colegiados");
        }

        return modelAndView;
    }

    @PostMapping("/atribuir")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ModelAndView atribuirProfessorNoColegiado(@RequestParam Long idProfessor,
                                                     @RequestParam Long idColegiado, ModelAndView modelAndView, BindingResult bindingResult, RedirectAttributes attr){

        //new ProcessoDTO(processoService.atribuirProcesso(idProcesso, idProfessor));
        //modelAndView.setViewName("redirect:/professores");

        ModelAndView mv = new ModelAndView("redirect:/professores");


        try {
            colegiadoService.adicionarProfessor(idColegiado, idProfessor);
            attr.addFlashAttribute("message", "OK: Colegiado atribuído com sucesso!");
            attr.addFlashAttribute("error", "false");
        } catch (RuntimeException e) {
            e.printStackTrace();
            attr.addFlashAttribute("message", "Error: " + e.getMessage());
            attr.addFlashAttribute("error", "true");
        }


        return mv;
    }

    @PatchMapping("/professor/{idColegiado}/{idProfessor}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado adicionaProfessor(@PathVariable Long idColegiado,
                                          @PathVariable Long idProfessor){
        return new Colegiado(colegiadoService.adicionarProfessor(idColegiado, idProfessor));
    }

    @DeleteMapping("/professor/{idColegiado}/{idProfessor}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado removeProfessor(@PathVariable Long idColegiado,
                                        @PathVariable Long idProfessor){
        return new Colegiado(colegiadoService.removerProfessor(idColegiado, idProfessor));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarColegiado(@PathVariable Long id){
        colegiadoService.deletarColegiado(id);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado buscaColegiado(@PathVariable Long id){
        return colegiadoService.findById(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado atualizaColegiado(@PathVariable Long id,
                                       @RequestBody @Valid Colegiado colegiado){
        return colegiadoService.atualizarColegiado(id, colegiado);
    }

    public boolean temcolegiado(Long id){
        return colegiadoService.temcolegiado(id);
    }

}
