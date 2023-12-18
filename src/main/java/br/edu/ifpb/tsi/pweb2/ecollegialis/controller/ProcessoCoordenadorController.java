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


@Controller
@RequestMapping("/coordenador/{id}")
public class ProcessoCoordenadorController {

    @Autowired
    private CoordenadorService coordenadorService;

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
    public Coordenador getCoordenador(@PathVariable("id") Long id){
        return this.coordenadorService.getCoordenadorPorId(id);
    }

    @ModelAttribute("relatores")
    public List<Professor> getRelatores(){
        return this.professorService.getProfessoresComProcessos();
    }

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessoresComColegiado();
    }

    @ModelAttribute("alunos")
    public List<Aluno> getAlunos(){
        return this.alunoService.getAlunosComProcessos();
    }


    //------ HOME -------
    @GetMapping
    public ModelAndView home(ModelAndView model){
        model.setViewName("/coordenador/home");
        return model;
    }


    //------ PROCESSOS ---------

    @GetMapping("processos")
    public ModelAndView listarProcessosCoordenador(ModelAndView model){
        model.addObject("professores", professorService.getProfessores());
        model.addObject("processos", processoService.getProcessos());
        model.setViewName("Coordenador/listarProcessoCoordenador");
        return model;
    }

    @GetMapping("processos/{idProcesso}")
    public ModelAndView exibirProcesso(ModelAndView model, @PathVariable("idProcesso") Long id){
        model.addObject("processo", processoService.getProcessoPorId(id));
        model.setViewName("Coordenador/atribuirProcesso");
        return model;
    }

    @PostMapping("processos/{idProcesso}")
    public ModelAndView salvarProcesso(
        ModelAndView model,
        Processo processo,
        @PathVariable("id")Long id,
        @PathVariable("idProcesso")Long idProcesso,
        RedirectAttributes redirectAttributes
    ){ 
            processoService.atribuirProcesso(processo,idProcesso);
            model.addObject("processos", processoService.getProcessos());
            model.setViewName("redirect:/coordenador/"+id+"/processos");
            redirectAttributes.addFlashAttribute("mensagem", "Processo designado com Sucesso");
            return model;
    }

    //------ REUNIÕES ---------

    @GetMapping("reunioes")
    public ModelAndView listarReunioes(ModelAndView model, @PathVariable("id") Long id){
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
        if (colegiado.getReunioes() != null && !colegiado.getReunioes().isEmpty()) {
            model.addObject("reunioes", colegiado.getReunioes());
        }
        model.setViewName("Coordenador/reunioes");
        return model;
    }

    @GetMapping("reunioes/criar")
    public ModelAndView criarReuniao(ModelAndView model,@PathVariable("id")Long id){
        List<Processo> processosDisponiveis = new ArrayList<Processo>();
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);

        for(Processo processo : colegiado.getProcessos()){
            if(processo.getRelator()!= null){
                processosDisponiveis.add(processo);
            }
        }

        List<Processo> processosEscolhidos = new ArrayList<Processo>();
        for(int i=0;i<5;i++){
            processosEscolhidos.add(new Processo());
        }
        Reuniao reuniao = new Reuniao(colegiado,processosEscolhidos);
        System.out.println(reuniao.getColegiado());
        model.addObject("colegiado", colegiado);
        model.addObject("processosEscolhidos", processosEscolhidos);
        model.addObject("processosDisponiveis", processosDisponiveis);
        model.addObject("reuniao", reuniao);
        model.setViewName("Coordenador/formReuniao");
        return model;
    }

    @PostMapping("reunioes/criar")
    public ModelAndView salvarReuniao(
        @Valid Reuniao reuniao,
        BindingResult validation, 
        ModelAndView model,
        @PathVariable("id")Long id, 
        RedirectAttributes redirectAttributes
        ){
        if (validation.hasErrors()) {
            List<Processo> processosDisponiveis = new ArrayList<Processo>();
            Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
            Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);

            for(Processo processo : colegiado.getProcessos()){
                if(processo.getRelator()!= null){
                    processosDisponiveis.add(processo);
                }
            }

            List<Processo> processosEscolhidos = new ArrayList<Processo>();
            for(int i=0;i<4;i++){
                processosEscolhidos.add(new Processo());
            }
            model.addObject("colegiado", colegiado);
            model.addObject("processosEscolhidos", processosEscolhidos);
            model.addObject("processosDisponiveis", processosDisponiveis);
            model.addObject("reuniao", reuniao);
            model.setViewName("Coordenador/formReuniao");
            return model;
        }
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);
        Colegiado colegiado = colegiadoService.getColegiadoPorCoordenador(coordenador);
        reuniao.setColegiado(colegiado);
        reuniaoService.salvarReuniao(reuniao);
        System.out.println(reuniao.getColegiado()); 
        model.addObject("reunioes", colegiado.getReunioes());
        model.setViewName("redirect:/coordenador/"+id+"/reunioes");
        redirectAttributes.addFlashAttribute("mensagem", "Reunião Criada com Sucesso");
        redirectAttributes.addFlashAttribute("reuniaoSalvos", true);
        return model;
    }
    
}
