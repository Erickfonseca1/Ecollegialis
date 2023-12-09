package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;


import java.util.List;

import javax.naming.Binding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/processo")
public class ProcessoController {

  @Autowired
  private ProcessoService processoService;

  @Autowired
  private AssuntoService assuntoService;
  

  @GetMapping("/new")
  public ModelAndView novoProcesso() {
    ModelAndView mv = new ModelAndView("Processo/formProcesso");
    List<Assunto> assuntos = assuntoService.findAll();
    mv.addObject("assuntos", assuntos);
    return mv;
  }

  @PostMapping("/salvar-processo")
  public String salvarProcesso(@Valid Processo processo, BindingResult result) {
    if (result.hasErrors()) {
      System.out.println(result.getAllErrors());
      return "Processo/formProcesso";
    }
    if (processo.getId() != null) {
      processoService.update(processo);
    } else {
      processoService.save(processo);
    }

    return "redirect:/processo/list";
  }
}
