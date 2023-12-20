package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

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
        model.setViewName("redirect:/coordenador/colegiados");
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

    //----- REUNIÕES -----
    @GetMapping("/reunioes")
    public ModelAndView listarReunioesProfessor(
            ModelAndView model,
            @PathVariable("id") Long id,
            @RequestParam(name = "status", required = false) String status) {

        Professor professor = professorService.getProfessorPorId(id);

        if (professor.getListaColegiados() != null && !professor.getListaColegiados().isEmpty()) {
            Colegiado colegiado = professor.getListaColegiados().get(0);

            if (colegiado != null) {
                List<Reuniao> reunioes;

                if ("finalizada".equalsIgnoreCase(status)) {
                    reunioes = reuniaoService.getReunioesFinalizadasDoColegiado(colegiado);
                } else if ("agendada".equalsIgnoreCase(status)) {
                    reunioes = reuniaoService.getReunioesAgendadasDoColegiado(colegiado);
                } else {
                    reunioes = reuniaoService.getReunioes();
                }

                model.addObject("reunioes", reunioes);
            }
        }
        model.setViewName("Professor/painel-reunioes");
        return model;
    }

    @GetMapping("/reunioes/{idReuniao}")
    public ModelAndView listarReunioes(ModelAndView model, @PathVariable("idReuniao") Long idReuniao){
        model.addObject("reuniao", this.reuniaoService.getReuniaoPorId(idReuniao));
        model.setViewName("Professor/reuniao");
        return model;
    }
}
