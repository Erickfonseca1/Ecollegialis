package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;

@Controller
@RequestMapping("/aluno")

public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private AssuntoRepository assuntoRepository;

    @GetMapping("/processo/cadastrar")
    public ModelAndView cadastrarProcesso(ModelAndView mav) {
        mav.setViewName("aluno/tela-aluno-cadastro-processo");
        mav.addObject("processo", new Processo());
        return mav;
    }

    @ModelAttribute("assuntoItens")
    public List<Assunto> getCorrentistas() {
        return assuntoRepository.findAll();
    }

    @ModelAttribute("statusItens")
    public List<StatusEnum> getStatus() {
        return List.of(StatusEnum.values());
    }

    @PostMapping("/processo/cadastrar")
    public ModelAndView salvarProcesso(Processo processo, ModelAndView modelAndView) {
        alunoService.cadastrarNovoProcesso(processo);
        modelAndView.setViewName("redirect:/aluno/processo");
        return modelAndView;

    }

    @GetMapping("/processo")
    public ModelAndView consultarProcessos(ModelAndView mav) {
        mav.setViewName("aluno/tela-aluno-listagem-processos");
        mav.addObject("processos", alunoService.consultaProcessos());
        return mav;

    }

    @PostMapping("/processo/consultar")
    public ModelAndView filtrarProcesso(String filtro, String order, ModelAndView modelAndView) {
        modelAndView.addObject("processos", alunoService.filtrarProcesso(filtro, order));
        modelAndView.setViewName("/aluno/tela-aluno-listagem-processos");
        return modelAndView;
    }
}
