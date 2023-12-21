package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/coordenador/processos")
@PreAuthorize("hasRole('COORDENADOR')")
public class ProcessoCoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @ModelAttribute("coordenador")
    public Coordenador getCoordenador(Principal principal) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        return this.coordenadorService.getCoordenadorPorProfessor(professor.getId());
    }

    @ModelAttribute("relatores")
    public List<Professor> getRelatores() {
        return this.professorService.getProfessoresComProcessos();
    }

    @ModelAttribute("professores")
    public List<Professor> getProfessores() {
        return this.professorService.getProfessoresComColegiado();
    }

    @ModelAttribute("alunos")
    public List<Aluno> getAlunos() {
        return this.alunoService.getAlunosComProcessos();
    }

    @ModelAttribute("processos")
    public List<Processo> getProcessos() {
        return this.processoService.getProcessos();
    }

    @GetMapping
    public ModelAndView listarProcessosCoordenador(ModelAndView model) {
        model.addObject("professores", professorService.getProfessores());
        model.addObject("processos", processoService.getProcessos());
        model.setViewName("Coordenador/listarProcessoCoordenador");
        return model;
    }

    @GetMapping("{idProcesso}/atribuir-relator")
    public ModelAndView exibirProcesso(ModelAndView model, @PathVariable("idProcesso") Long id) {
        model.addObject("processo", processoService.getProcessoPorId(id));
        model.setViewName("Coordenador/atribuirProcesso");
        return model;
    }

    @PostMapping("{idProcesso}/atribuir-relator")
    public ModelAndView salvarProcesso(
            ModelAndView model,
            Processo processo,
            @PathVariable("idProcesso") Long idProcesso,
            RedirectAttributes redirectAttributes) {
        processoService.atribuirRelator(processo, idProcesso);
        model.addObject("processos", processoService.getProcessos());
        model.setViewName("redirect:/coordenador/processos");
        return model;
    }
}
