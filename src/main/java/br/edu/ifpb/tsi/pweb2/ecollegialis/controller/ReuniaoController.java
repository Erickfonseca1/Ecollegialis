package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusReuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ReuniaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class ReuniaoController {
    @Autowired
    ReuniaoService reuniaoService;
    @Autowired
    ProcessoService processoService;
    @Autowired
    ColegiadoService colegiadoService;
    @Autowired
    ProfessorService professorService;

    @PostMapping("/criar")
    public ModelAndView criarReuniao(
            @RequestParam Long idColegiadoReuniao,
            @RequestParam Long[] idProcessoReuniao,
            RedirectAttributes attr
    ) {
        Colegiado colegiado = colegiadoService.findById(idColegiadoReuniao);
        List<Processo> processos = new ArrayList<>();

        for (Long processoVerificar : idProcessoReuniao) {
            Optional<Processo> processoOptional = Optional.ofNullable(processoService.findById(processoVerificar));
            Processo processo = processoOptional.orElseThrow(() -> new NoSuchElementException("Processo não encontrado"));

            if (processo.getRelator() == null) {
                attr.addFlashAttribute("message", "Error: Processo não tem professor");
                attr.addFlashAttribute("error", "true");
                return new ModelAndView("redirect:/processos");
            }

            if (processo.getRelator().getColegiado() == null) {
                attr.addFlashAttribute("message", "Error: Professor não faz parte do colegiado!");
                attr.addFlashAttribute("error", "true");
                return new ModelAndView("redirect:/processos");
            }

            if (!processo.getRelator().getColegiado().getCurso().equals(colegiado.getCurso())) {
                attr.addFlashAttribute("message", "Error: Professor não faz parte do colegiado!");
                attr.addFlashAttribute("error", "true");
                return new ModelAndView("redirect:/processos");
            }
            System.out.println(processo.toString());

            processos.add(processo);
        }

        try {
            Reuniao reuniao = new Reuniao(colegiado, processos, StatusReuniao.PROGRAMADA);
            reuniaoService.cadastraNovaReuniao(processos, reuniao);
            attr.addFlashAttribute("message", "Reunião criada com sucesso!");
        } catch (RuntimeException e) {
            e.printStackTrace();
            attr.addFlashAttribute("message", "Error: " + e.getMessage());
            attr.addFlashAttribute("error", "true");
        }
        return new ModelAndView("redirect:/reunioes");
    }

    @GetMapping
    public ModelAndView listaReunioes(ModelAndView modelAndView) {
        List<Reuniao> reunioes = reuniaoService.listarReunioes();
        for (Reuniao reuniao : reunioes) {
            System.out.println(reuniao.toString());
            System.out.println(); // Adiciona uma linha em branco entre as reuniões
        }

        modelAndView.addObject("reunioes", reunioes);
        modelAndView.setViewName("reunioes/index");
        return modelAndView;
    }

    @GetMapping ("/{id}/listreuniaocolegiado")
    public ModelAndView listaReunioesDoColegiado(@PathVariable Long id, ModelAndView modelAndView){

        Professor professor = professorService.findById(id);

        List<Reuniao> reunioes = reuniaoService.reunioesdocolegiado(professor.getColegiado().getId());


        modelAndView.addObject("reunioescolegiado", reunioes);
        modelAndView.addObject("statusReuniao", StatusReuniao.values());
        modelAndView.addObject("professor", professor);
        modelAndView.setViewName("reunioes/listarreunioescolegiado");

        return modelAndView;

    }

    @GetMapping("/filtrar/{id}")
    public ModelAndView filtrar (@PathVariable Long id, @RequestParam (name = "statusFilter", required = false) StatusReuniao status) {

        ModelAndView mv = new ModelAndView("reunioes/listarreunioescolegiado");
        System.out.println(status);
        Professor professor = professorService.findById(id);
        Long idcolegiado = professor.getColegiado().getId();
        List<Reuniao> reunioesfiltradas = reuniaoService.filtrarreuniao(status, idcolegiado);

        mv.addObject("reunioescolegiado", reunioesfiltradas);
        mv.addObject("statusReuniao", StatusReuniao.values());
        mv.addObject("professor", professor);
        return mv;

    }

    @PostMapping("/reuniao/iniciar")
    public ModelAndView iniciarReuniao(Long idReuniao){
        reuniaoService.iniciarReuniao(idReuniao);
        return null;
    }
    @PostMapping("/reuniao/encerrar")
    public ModelAndView encerrarReuniao(Long idReuniao){
        reuniaoService.encerrarReuniao(idReuniao);
        return null;
    }
}
