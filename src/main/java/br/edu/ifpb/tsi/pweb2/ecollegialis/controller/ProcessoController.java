package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;


import java.util.List;

import javax.naming.Binding;

import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.VotoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import jakarta.validation.Valid;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/processo")
public class ProcessoController {

  @Autowired
  private ProcessoService processoService;

  @Autowired
  private VotoRepository votoRepository;

  @Autowired
  private AssuntoService assuntoService;

  @Autowired
  private ProfessorService professorService;

  @Autowired
  private AlunoService alunoService;


  @ResponseBody
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/{idAluno}")
  public void criarProcesso(@PathVariable Long idAluno, @RequestBody Processo processo){
    processo.setAlunoProcesso(alunoService.findById(idAluno));
    processo.setAssunto(assuntoService.findById(processo.getAssunto().getId()));
    processoService.save(processo);
  }

  @GetMapping("/new")
  public ModelAndView novoProcesso() {
    ModelAndView mv = new ModelAndView("Processo/formProcesso");
    List<Assunto> assuntos = assuntoService.findAll();
    mv.addObject("assuntos", assuntos);
    return mv;
  }

  @GetMapping("/list")
  public ModelAndView listarProcessos() {
    ModelAndView mv = new ModelAndView("Processo/ListaProcessos");
    List<Processo> processos = processoService.findAll();
    mv.addObject("processos", processos);
    return mv;
  }
}
