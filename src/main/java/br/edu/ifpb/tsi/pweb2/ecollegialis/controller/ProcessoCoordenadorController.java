package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/coordenador/{id}")
public class ProcessoCoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AlunoService alunoService;

    @Autowired
    private ColegiadoService colegiadoService;

    @Autowired
    private ReuniaoService reuniaoService;

    @ModelAttribute("coordenador")
    public Coordenador getCoordenador(@PathVariable("id") Long id) {
        return this.coordenadorService.getCoordenadorPorId(id);
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

    @GetMapping("processos")
    public ModelAndView listarProcessosCoordenador(ModelAndView model) {
        model.addObject("professores", professorService.getProfessores());
        model.addObject("processos", processoService.getProcessos());
        model.setViewName("Coordenador/listarProcessoCoordenador");
        return model;
    }

    @GetMapping("processos/{idProcesso}")
    public ModelAndView exibirProcesso(ModelAndView model, @PathVariable("idProcesso") Long id) {
        model.addObject("processo", processoService.getProcessoPorId(id));
        model.setViewName("Coordenador/atribuirProcesso");
        return model;
    }

    @PostMapping("processos/{idProcesso}")
    public ModelAndView salvarProcesso(
            ModelAndView model,
            Processo processo,
            @PathVariable("id") Long id,
            @PathVariable("idProcesso") Long idProcesso,
            RedirectAttributes redirectAttributes) {
        processoService.atribuirProcesso(processo, idProcesso);
        model.addObject("processos", processoService.getProcessos());
        model.setViewName("redirect:/coordenador/" + id + "/processos");
        return model;
    }

    @GetMapping("processos/{idProcesso}/colegiado")
    public ModelAndView exibirProcessoColegiado(ModelAndView model, @PathVariable("idProcesso") Long id) {
        model.addObject("processo", processoService.getProcessoPorId(id));
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("Coordenador/atribuirColegiado");
        return model;
    }
    @PostMapping("processos/{idProcesso}/colegiado/criar")
    public ModelAndView atribuirProcessoColegiado(
            ModelAndView model,
            @ModelAttribute("processo") Processo processo,
            @PathVariable("id") Long id,
            @PathVariable("idProcesso") Long idProcesso) {
        colegiadoService.atribuirProcessoAoColegiado(processo, idProcesso);
        model.setViewName("redirect:/coordenador/" + id + "/processos");
        return model;
    }

    @GetMapping("reunioes")
    public ModelAndView listarReunioes(ModelAndView model, @PathVariable("id") Long id) {
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        if (coordenador != null) {
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            if (colegiado != null) {
                model.addObject("reunioes", colegiado.getReunioes());
            }
        }
        model.setViewName("Coordenador/reunioes");
        return model;
    }

    @GetMapping("reunioes/criar")
    public ModelAndView criarReuniao(ModelAndView model, @PathVariable("id") Long id) {
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = coordenador != null ? colegiadoService.getColegiadoPorCoordenador(coordenador) : null;

        List<Processo> processosDisponiveis = colegiado != null ? colegiado.getProcessos().stream()
                .filter(processo -> processo.getRelator() != null)
                .collect(Collectors.toList()) : new ArrayList<>();

        List<Processo> processosEscolhidos = new ArrayList<>();
        Reuniao reuniao = new Reuniao(colegiado, processosEscolhidos);

        model.addObject("colegiado", colegiado);
        model.addObject("processosEscolhidos", processosEscolhidos);
        model.addObject("processosDisponiveis", processosDisponiveis);
        model.addObject("reuniao", reuniao);

        model.setViewName("Coordenador/criar-reuniao");
        return model;
    }

    @PostMapping("reunioes/criar")
    public ModelAndView salvarReuniao(
            @Valid Reuniao reuniao,
            BindingResult validation,
            ModelAndView model,
            @PathVariable("id") Long id,
            RedirectAttributes redirectAttributes) {

        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = coordenador != null ? colegiadoService.getColegiadoPorCoordenador(coordenador) : null;

        if (colegiado != null) {
            if (validation.hasErrors()) {
                List<Processo> processosDisponiveis = colegiado.getProcessos().stream()
                        .filter(processo -> processo.getRelator() != null)
                        .collect(Collectors.toList());

                List<Processo> processosEscolhidos = new ArrayList<>();
                model.addObject("colegiado", colegiado);
                model.addObject("processosEscolhidos", processosEscolhidos);
                model.addObject("processosDisponiveis", processosDisponiveis);
                model.addObject("reuniao", reuniao);
                model.setViewName("Coordenador/criar-reuniao");
                return model;
            }

            reuniao.setColegiado(colegiado);
            reuniaoService.salvarReuniao(reuniao);

            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/coordenador/" + id + "/reunioes");
            redirectAttributes.addFlashAttribute("mensagem", "Reunião Criada com Sucesso");
        }

        return model;
    }

    @GetMapping("reunioes/{idReuniao}")
    public ModelAndView listarReuniao(ModelAndView model, @PathVariable("id") Long id, @PathVariable("idReuniao") Long idReuniao) {
        model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
        model.setViewName("Coordenador/reuniao");
        return model;
    }

    @PostMapping("reunioes/{idReuniao}/iniciar")
    public ModelAndView iniciarReuniao(Reuniao reuniao, ModelAndView model, @PathVariable("id") Long id, @PathVariable("idReuniao") Long idReuniao, RedirectAttributes redirectAttributes) {
        try {
            this.reuniaoService.iniciarReuniao(reuniao, idReuniao);
            model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
            model.setViewName("redirect:/coordenador/" + id + "/reunioes/" + idReuniao + "/painel");
            return model;
        } catch (Exception e) {
            Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/coordenador/" + id + "/reunioes");
            return model;
        }
    }

    @GetMapping("reunioes/{idReuniao}/painel")
    public ModelAndView listarReuniaoNaTela(ModelAndView model, @PathVariable("id") Long id, @PathVariable("idReuniao") Long idReuniao) {
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);
        model.addObject("processo", reuniao.getProcessos().get(0));
        model.addObject("reuniao", reuniao);
        model.setViewName("Coordenador/painel-reuniao");
        return model;
    }

    @GetMapping("reunioes/{idReuniao}/painel/{idProcesso}")
    public ModelAndView listarReuniaoNaTela(
            ModelAndView model,
            @PathVariable("id") Long id,
            @PathVariable("idReuniao") Long idReuniao,
            @PathVariable("idProcesso") Long idProcesso) {
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);
        Processo processo = this.processoService.getProcessoPorId(idProcesso);
        Colegiado colegiado = reuniao.getColegiado();
        List<Voto> listaVotos = new ArrayList<Voto>();
        for (Professor membro : colegiado.getMembros()) {
            Voto voto = new Voto();
            if (membro == processo.getRelator()) {
                Coordenador coordenador = colegiado.getCoordenador();
                Professor professor = coordenador.getProfessor();
                voto.setProfessor(professor);
            } else {
                Professor professor = membro;
                voto.setProfessor(professor);
            }
            voto.setProcesso(processo);
            listaVotos.add(voto);
        }
        processo.setListaDeVotos(listaVotos);
        System.out.println(processo.getListaDeVotos());
        model.addObject("processo", processo);
        model.addObject("listaVotos", listaVotos);
        model.addObject("reuniao", reuniao);
        model.setViewName("Coordenador/painel-reuniao");
        return model;
    }

    @PostMapping("reunioes/{idReuniao}/painel/{idProcesso}")
    public ModelAndView processoJulgado(
            Processo processo,
            ModelAndView model,
            @PathVariable("id") Long id,
            @PathVariable("idReuniao") Long idReuniao,
            @PathVariable("idProcesso") Long idProcesso) {
        System.out.println(processo.getListaDeVotos());
        processoService.julgarProcesso(processo, idProcesso);
        Processo novoProcesso = processoService.getProcessoPorId(idProcesso);
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);
        List<Voto> listaVotos = new ArrayList<Voto>();
        for (Professor membro : reuniao.getColegiado().getMembros()) {
            Voto voto = new Voto();
            voto.setProcesso(novoProcesso);
            voto.setProfessor(membro);
            listaVotos.add(voto);
        }
        novoProcesso.setListaDeVotos(listaVotos);
        model.addObject("processo", novoProcesso);
        model.addObject("listaVotos", listaVotos);
        model.addObject("reuniao", reuniao);
        model.setViewName("redirect:/coordenador/" + id + "/reunioes/" + idReuniao + "/painel/" + idProcesso);
        return model;
    }

    @PostMapping("reunioes/{idReuniao}/painel/encerrar")
    public ModelAndView encerrarReuniao(Reuniao reuniao, ModelAndView model, @PathVariable("id") Long id,
            @PathVariable("idReuniao") Long idReuniao) {
        reuniaoService.encerrarReuniao(reuniao, idReuniao);
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
        model.addObject("reunioes", colegiado.getReunioes());
        model.setViewName("/coordenador/painel-reunioes");
        return model;
    }
}
