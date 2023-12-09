package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;

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

  @GetMapping("/list")
  public ModelAndView listaProcessos() {
    ModelAndView mv = new ModelAndView("Processo/listaProcessos");
    return mv;
  }

  @GetMapping("/edit")
  public ModelAndView editarProcesso() {
    ModelAndView mv = new ModelAndView("Processo/formProcesso");
    return mv;
  }
}
