package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoDecisao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.TipoVoto;
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

    @Autowired
    private VotoService votoService;

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
    public ModelAndView listarReunioes(ModelAndView model, Principal principal) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
        if (coordenador != null) {
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            if (colegiado != null) {
                model.addObject("reunioes", colegiado.getReunioes());
            }
        }

        if (professor != null) {
            // achar as reuniões de colegiado que o professor participa
            Colegiado colegiado = professor.getListaColegiados().get(0);
            List<Reuniao> reunioes = colegiado.getReunioes();

            //para ver de qual reunião o professor é membro
            List<Reuniao> reunioesProfessor = new ArrayList<>();
            for (Reuniao reuniao : reunioes) {
                if (reuniao.getColegiado().getMembros().contains(professor)) {
                    reunioesProfessor.add(reuniao);
                }
            } 

            model.addObject("reunioes", reunioesProfessor);
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


        // filtrar processos do colegiado que não possuam status de "EM_PAUTA, pois já está numa reunião
        // "EM_JULGAMENTO" e "JULGADO", pois já estão em trâmite e também que possuam relator
        List<Processo> processosDisponiveis = colegiado != null ? colegiado.getProcessos().stream()
                .filter(processo -> processo.getStatus() != StatusEnum.EM_PAUTA && processo.getStatus() != StatusEnum.EM_JULGAMENTO && processo.getStatus() != StatusEnum.JULGADO && processo.getRelator() != null)
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
    @PreAuthorize("hasRole('COORDENADOR')")
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
                        .filter(processo -> processo.getRelator() != null && processo.getStatus() == StatusEnum.CRIADO || processo.getRelator() != null && processo.getStatus() == StatusEnum.DISTRIBUIDO)
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
    public ModelAndView listarReuniao(ModelAndView model, @PathVariable("idReuniao") Long idReuniao, Principal principal, RedirectAttributes redirectAttributes) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);
        Colegiado colegiado = reuniao.getColegiado();

        boolean reuniaoEmAndamento = this.reuniaoService.temReuniaoEmAndamento(colegiado);
        // Map<Processo, Boolean> votosProfessor = new HashMap<>();
        // for (Processo processo : reuniao.getProcessos()) {
        //     boolean professorVotou = processo.professorVotou(professor);
        //     votosProfessor.put(processo, professorVotou);
        // }

        boolean reuniaoPodeEncerrar = this.reuniaoService.todosProcessosJulgados(reuniao);
        System.out.println("reuniao pode encerrar: " + reuniaoPodeEncerrar);

        // model.addObject("votosProfessor", votosProfessor);
        model.addObject("reuniaoEmAndamento", reuniaoEmAndamento);
        model.addObject("reuniaoPodeEncerrar", reuniaoPodeEncerrar);
        model.addObject("mensagem", "Não é possível encerrar a reunião, pois há processos que ainda não foram julgados");
        model.addObject("professor", professor);
        model.addObject("reuniao", reuniao);
        model.setViewName("Coordenador/reuniao");
        return model;
    }

    @PostMapping("reunioes/iniciar/{idReuniao}")
    public ModelAndView iniciarReuniao(Reuniao reuniao, ModelAndView model, Principal principal, @PathVariable("idReuniao") Long idReuniao, RedirectAttributes redirectAttributes) {
        try {
            this.reuniaoService.iniciarReuniao(reuniao, idReuniao);
            model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
            boolean reuniaoIniciou = true;
            model.addObject("reuniaoIniciou", reuniaoIniciou);
            model.setViewName("redirect:/colegiados/reunioes/" + idReuniao);
            return model;
        } catch (Exception e) {
            Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
            Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/colegiados/reunioes");
            return model;
        }
    }

    @PostMapping("reunioes/encerrar/{idReuniao}")
    public ModelAndView encerrarReuniao(ModelAndView model, Principal principal, @PathVariable("idReuniao") Long idReuniao, RedirectAttributes redirectAttributes) {
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);

        boolean reuniaoPodeEncerrar = this.reuniaoService.todosProcessosJulgados(reuniao);
        System.out.println("reuniao pode encerrar: " + reuniaoPodeEncerrar);
        
        if (reuniaoPodeEncerrar) {
            this.reuniaoService.encerrarReuniao(reuniao, idReuniao);
            model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
            model.addObject("reuniaoPodeEncerrar", reuniaoPodeEncerrar);
            
            model.setViewName("redirect:/colegiados/reunioes/" + idReuniao);
            return model;
        } else {
            Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
            Coordenador coordenador = coordenadorService.getCoordenadorPorProfessor(professor.getId());
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
            //passar uma mensagem de que não é possível encerrar a reunião
            redirectAttributes.addFlashAttribute("mensagem", "Não é possível encerrar a reunião, pois há processos que ainda não foram julgados");
            model.addObject("reunioes", colegiado.getReunioes());
            model.setViewName("redirect:/colegiados/reunioes/" + idReuniao);
            return model;
        }

    }

    @GetMapping("reunioes/{idReuniao}/processo/{idProcesso}")
    public ModelAndView telaVoto(ModelAndView model, @PathVariable("idReuniao") Long idReuniao, @PathVariable("idProcesso") Long idProcesso, Principal principal) {
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);
        Processo processo = this.processoService.getProcessoPorId(idProcesso);
        
        TipoDecisao decisaoTemporaria = processo.getTipoDecisao();
        String numProcesso = processo.getNumero();

        // achar professor relator do processo
        Professor relator = processo.getRelator();
        Voto voto = new Voto();


        model.addObject("professor", professor);
        model.addObject("reuniao", reuniao);
        model.addObject("processo", processo);
        model.addObject("relator", relator);
        model.addObject("decisaoTemporaria", decisaoTemporaria);
        model.addObject("numProcesso", numProcesso);
        model.addObject("voto", voto);
        model.setViewName("Professor/votoProfessor");
        return model;
    }


    @PostMapping("reunioes/{idReuniao}/processo/{idProcesso}/votar")
    public ModelAndView votar(ModelAndView model, @PathVariable("idReuniao") Long idReuniao, @PathVariable("idProcesso") Long idProcesso, Voto voto, Principal principal) {
        // adicionar o voto que vem do form à listaDeVotos do processo, e salvar o processo
        // caso a listaDeVotos esteja vazia e receba o primeiro voto, o processo é alterado para "EM_JULGAMENTO"
        Processo processo = this.processoService.getProcessoPorId(idProcesso);
        Professor professor = this.professorService.getProfessorPorMatricula(principal.getName());
        Reuniao reuniao = this.reuniaoService.getReuniaoPorId(idReuniao);

        voto.setProfessor(professor);
        voto.setProcesso(processo);
        List<Voto> listaVotosAtual = processo.getListaDeVotos();
        System.out.println("lista de votos atual: " + listaVotosAtual);
        this.votoService.salvarVoto(voto);
        

        if (listaVotosAtual.isEmpty()) {    
            processo.setStatus(StatusEnum.EM_JULGAMENTO);
            System.out.println("status do processo: " + processo.getStatus());
        }

        listaVotosAtual.add(voto);

        // caso o numero de votos seja igual ao numero de membros da reuniao, com exceção do professor relator
        // o processo é alterado seu status para "JULGADO" e o resultado é definido
        if (listaVotosAtual.size() == processo.getReuniao().getColegiado().getMembros().size() - 1) {
            
            System.out.println("status do processo: " + processo.getStatus());

            // verifica se há mais votos contrários ou a favor do voto do relator
            int votosContra = 0;
            int votosFavor = 0;

            String divergente = "DIVERGENTE";

            for (Voto votoAtual : listaVotosAtual) {
                if (votoAtual.getTipoVoto().toString() == divergente) {
                    votosContra++;
                } else {
                    votosFavor++;
                }
            }

            if (votosContra > votosFavor) {
                System.out.println("mais votos contra o relator");
                if (processo.getTipoDecisao() == TipoDecisao.DEFERIDO) {
                    processo.setTipoDecisao(TipoDecisao.INDEFERIDO);

                    System.out.println("tipo de decisão: " + processo.getTipoDecisao());
                } else {
                    processo.setTipoDecisao(processo.getTipoDecisao());
                    System.out.println("tipo de decisão: " + processo.getTipoDecisao());
                }
            } else {
                System.out.println("mais votos a favor do relator");
                processo.setTipoDecisao(processo.getTipoDecisao());
            }

            processo.setStatus(StatusEnum.JULGADO);
            System.out.println("status do processo: " + processo.getStatus());

            this.processoService.atualizarProcessoParaJulgado(processo, idProcesso);
            model.setViewName("redirect:/colegiados/reunioes/" + idReuniao);
            return model;
        }

        System.out.println("lista de votos atual: " + listaVotosAtual);
        processo.setListaDeVotos(listaVotosAtual);

        System.out.println("dados do processo: " + processo.getListaDeVotos() + " " + processo.getStatus() + " " + processo.getTipoDecisao());
        this.processoService.atualizarProcesso(processo, idProcesso);
        model.setViewName("redirect:/colegiados/reunioes/" + idReuniao);
        return model;
    } 

    // @GetMapping("reunioes/{idReuniao}/{idProcesso}")
    // public ModelAndView listarReuniaoNaTela(
    //         ModelAndView model,
    //         Principal principal,
    //         @PathVariable("idReuniao") Long idReuniao,
    //         @PathVariable("idProcesso") Long idProcesso) {
    //     Reuniao reuniao = reuniaoService.getReuniaoPorId(idReuniao);
    //     Processo processo = processoService.getProcessoPorId(idProcesso);
    //     Colegiado colegiado = reuniao.getColegiado();
    //     List<Voto> listaVotos = new ArrayList<>();
    //     for (Professor membro : colegiado.getMembros()) {
    //         Voto voto = new Voto();
    //         Professor professor = (membro == processo.getRelator()) ? colegiado.getCoordenador().getProfessor()
    //                 : membro;

    //         voto.setProfessor(professor);
    //         voto.setProcesso(processo);
    //         listaVotos.add(voto);
    //     }
    //     processo.setListaDeVotos(listaVotos);
    //     model.addObject("processo", processo);
    //     model.addObject("listaVotos", listaVotos);
    //     model.addObject("reuniao", reuniao);
    //     model.setViewName("Coordenador/painel-reuniao");
    //     return model;
    // }

   
}
