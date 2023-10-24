package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/assunto")
@AllArgsConstructor
public class AssuntoController {
  private final AssuntoService assuntoService;

  @PostMapping
  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  public Assunto criarAssunto() {
    return assuntoService.criarAssunto();
  }

  @PutMapping("/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Assunto atualizAssunto(Long id) {
    return assuntoService.atualizarAssunto(id);
  }

  @DeleteMapping("/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletarAssunto(Long id) {
    assuntoService.deletarAssunto(id);
  }

  @GetMapping("/{id}")
  @ResponseBody
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Assunto getAssunto(Long id) {
    return assuntoService.encontrarPorId(id);
  }

}
