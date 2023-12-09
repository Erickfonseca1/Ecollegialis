package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;

@Controller
@RequestMapping("/processo")
public class ProcessoController {

  @GetMapping("/new")
  public ModelAndView novoProcesso() {
    ModelAndView mv = new ModelAndView("Processo/formProcesso");
    mv.addObject("processo", new Processo());
    return mv;
  }
}
