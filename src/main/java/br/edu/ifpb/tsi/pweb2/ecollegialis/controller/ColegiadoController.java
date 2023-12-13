package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/colegiados")
public class ColegiadoController {

    @Autowired
    private ColegiadoService colegiadoService;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private CursoService cursoService;

    @Autowired
    private CoordenadorService coordenadorService;

    @Autowired
    private AlunoService alunoService;

    @ModelAttribute("professores")
    public List<Professor> getProfessores(){
        return this.professorService.getProfessores();
    }

    @GetMapping("professor/{id}")
    public ModelAndView listColegiados(@PathVariable("id")Long id, ModelAndView model){
        Coordenador coordenador = this.coordenadorService.getCoordenadorPorId(id);
        model.addObject("coordenador", coordenador);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("Colegiado/listaColegiados");
        return model;
    }

    @GetMapping("criar/{id}")
    public ModelAndView createColegiado(ModelAndView model, @PathVariable("id") Long id ,RedirectAttributes redirectAttributes ){
        Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);

        //listar professores que são do mesmo curso do coordenador, excluindo o coordenador
        List<Professor> professores = professorService.getProfessoresPorCurso(coordenador.getCurso().getId());
        professores.remove(coordenador.getProfessor());

        //listar alunos que são do mesmo curso do coordenador
        List<Aluno> alunos = alunoService.getAlunosDeUmCurso(coordenador.getCurso().getId());

        //curso do coordenador
        Curso curso = coordenador.getCurso();

        model.addObject("curso", curso);
        model.addObject("coordenador", coordenador);
        model.addObject("professores", professores);
        model.addObject("alunos", alunos);
        model.addObject("colegiado", new Colegiado(professores));
        model.addObject("acao", "salvar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("criar/{id}")
    public ModelAndView saveColegiado(
            @Valid Colegiado colegiado,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            Coordenador coordenador = coordenadorService.getCoordenadorPorId(id);

            //listar professores que são do mesmo curso do coordenador
            List<Professor> professores = professorService.getProfessoresPorCurso(coordenador.getCurso().getId());

            //curso do coordenador
            Curso curso = coordenador.getCurso();

            model.addObject("curso", curso);
            model.addObject("coordenador", coordenador);
            model.addObject("professores", professores);
            model.addObject("colegiado", new Colegiado(professores));
            model.addObject("acao", "salvar");
            model.setViewName("Colegiado/formColegiado");
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem", "Colegiado Criado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosSalvo", true);
        return model;
    }

    @GetMapping("criar")
    public ModelAndView createColegiado(ModelAndView model, RedirectAttributes redirectAttributes ){
        List<Professor> membros = new ArrayList<Professor>();
        for(int i=0 ; i<4;i++){
            membros.add(new Professor());
        }
        model.addObject("membros", membros);
        model.addObject("colegiado", new Colegiado(membros));
        model.addObject("acao", "salvar");
        model.setViewName("Colegiado/formColegiado");
        return model;
    }

    @PostMapping("criar")
    public ModelAndView saveColegiado(
            @Valid Colegiado colegiado,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            for(int i=0 ; i<4;i++){
                membros.add(new Professor());
            }
            model.addObject("membros", membros);
            model.setViewName("Colegiado/formColegiado");
            model.addObject("acao", "salvar");
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem", "Colegiado Criado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosSalvo", true);
        return model;
    }

    @GetMapping("{id}")
    public ModelAndView editColegiado(@PathVariable("id") long id, ModelAndView model, RedirectAttributes redirectAttributes){
        List<Professor> membros = new ArrayList<Professor>();
        for(int i=0 ; i<4;i++){
            membros.add(new Professor());
        }
        model.addObject("membros", membros);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.addObject("acao", "editar");
        model.setViewName("Colegiado/formColegiado");
        redirectAttributes.addFlashAttribute("mensagem","Colegiado Editado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosEditado", true);
        return model;
    }

    @PostMapping("{id}")
    public ModelAndView updateColegiado(
            @Valid Colegiado colegiado,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            List<Professor> membros = new ArrayList<Professor>();
            for(int i=0 ; i<4;i++){
                membros.add(new Professor());
            }
            model.addObject("membros", membros);
            model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
            model.setViewName("redirect:/colegiados/"+id);
            return model;
        }
        colegiadoService.salvarColegiado(colegiado);
        model.addObject("colegiado", colegiadoService.getColegiadoPorId(id));
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem", "Colegiado Editado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadosEditado", true);
        return model;
    }


    @RequestMapping("{id}/delete")
    public ModelAndView deleteColegiado(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes){
        colegiadoService.deletarColegiado(id);
        model.addObject("colegiados", colegiadoService.getColegiados());
        model.addObject("colegiado", new Colegiado(new ArrayList<Professor>()));
        model.setViewName("redirect:/colegiados");
        redirectAttributes.addFlashAttribute("mensagem","Colegiado Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("colegiadoDeletado", true);
        return model;
    }
}

