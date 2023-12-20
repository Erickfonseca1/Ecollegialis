package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/colegiados")
@PreAuthorize("hasAnyRole('COORDENADOR', 'PROFESSOR')")
public class ColegiadosController {
    
    @Autowired
    private ColegiadoService colegiadoService;

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private ProcessoService processoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private ReuniaoService reuniaoService;

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessores();
    }

    @ModelAttribute("cursos")
    public List<Curso> getCursos(){
        return this.adminService.getCursos();
    }

    @ModelAttribute("coordenadores")
    public List<Coordenador> getCoordenadores(){
        return this.coordenadorService.getCoordenadores();
    }

    @GetMapping
    public ModelAndView listarColegiados(ModelAndView model){
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("Colegiado/listaColegiados");
        return model;
    }

    @GetMapping("criar")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ModelAndView criarColegiados(ModelAndView model, Principal principal){
        List<Professor> membros = new ArrayList<Professor>();
        //buscar o nome do professor associado via id ao coordenador
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Coordenador coordenador = this.coordenadorService.getCoordenadorPorProfessor(professor.getId());

        model.addObject("colegiado", new Colegiado(membros));
        model.addObject("coordenador", coordenador);
        model.addObject("membros", membros);
        model.addObject("acao", "salvar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("criar")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ModelAndView salvarColegiados(
            @Valid Colegiado colegiado,
            BindingResult validation,
            ModelAndView model
    ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            model.addObject("membros", membros);
            model.setViewName("Colegiado/formColegiado");
            model.addObject("acao", "salvar");
            return model;
        }
        colegiado.setCoordenador(this.coordenadorService.getCoordenadorPorCurso(colegiado.getCurso().getId()));
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("redirect:/colegiados");
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editarColegiados(@PathVariable("id") long id, ModelAndView model){
        List<Professor> membros = new ArrayList<Professor>();
        model.addObject("membros", membros);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView atualizarColegiados(
        @Valid Colegiado colegiado, 
        BindingResult validation,
        @PathVariable("id") Long id,
        ModelAndView model
        ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            model.addObject("membros", membros);
            model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
            model.setViewName("redirect:/colegiados/"+id);
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.setViewName("redirect:/colegiados");
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deletarColegiados(@PathVariable("id") Long id, ModelAndView model){
        colegiadoService.deletarColegiado(id);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.addObject("colegiado", new Colegiado(new ArrayList<Professor>()));
        model.setViewName("redirect:/colegiados");
        return model;
    }

    
    @GetMapping("reunioes")
    @PreAuthorize("hasRole('COORDENADOR')")
    public ModelAndView listarReunioes(ModelAndView model, Principal principal) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
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
    @PreAuthorize("hasRole('COORDENADOR')")
    public ModelAndView criarReuniao(ModelAndView model, Principal principal) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());

        Colegiado colegiado = coordenador != null ? colegiadoService.getColegiadoPorCoordenador(coordenador) : null;
        List<Professor> membros = colegiado.getMembros();

        List<Processo> processosDisponiveis = colegiado != null ? colegiado.getProcessos().stream()
                .filter(processo -> processo.getRelator() != null)
                .collect(Collectors.toList()) : new ArrayList<>();

        List<Processo> processosEscolhidos = new ArrayList<>();
        Reuniao reuniao = new Reuniao(colegiado, processosEscolhidos);

        model.addObject("colegiado", colegiado);
        model.addObject("membros", membros);
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
            Principal principal,
            RedirectAttributes redirectAttributes) {

        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
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
            reuniao.setStatus(StatusReuniao.PROGRAMADA);
            reuniaoService.salvarReuniao(reuniao);

            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/colegiados/reunioes");
            redirectAttributes.addFlashAttribute("mensagem", "Reunião Criada com Sucesso");
        }

        return model;
    }

    @GetMapping("reunioes/{idReuniao}")
    public ModelAndView listarReuniao(ModelAndView model, @PathVariable("idReuniao") Long idReuniao) {
        model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
        model.setViewName("Coordenador/reuniao");
        return model;
    }

    @PostMapping("{idReuniao}/iniciar")
    public ModelAndView iniciarReuniao(Reuniao reuniao, ModelAndView model, Principal principal, @PathVariable("idReuniao") Long idReuniao, RedirectAttributes redirectAttributes) {
        try {
            this.reuniaoService.iniciarReuniao(reuniao, idReuniao);
            model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
            model.setViewName("redirect:/coordenador/reunioes/" + idReuniao);
            return model;
        } catch (Exception e) {
            Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
            Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/coordenador/reunioes");
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
