package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Coordenador;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Curso;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AdminService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private ProfessorService professorService;
    
    @GetMapping("/home")
    public ModelAndView home(ModelAndView model, Principal principal, HttpSession session) {
        model.setViewName("home");
        
        session.setAttribute("nomeCompleto", "Admin");
        session.setAttribute("nome", "Admin");
        model.addObject("titulo", "Administrador");
        model.addObject("texto", "O administrador é responsável por gerenciar diversos itens do sistema, como cursos, professores e coordenadores, dentre outros atributos essenciais.");
        return model;
    }

    // Cursos

    @GetMapping("/cursos")
    public ModelAndView listCursos(ModelAndView model) {
        model.addObject("cursos", adminService.getCursos());
        model.addObject("curso", new Curso());
        model.setViewName("Cursos/listaCursos");
        return model;
    }

    @GetMapping("/cursos/criar")
    public ModelAndView criarCurso(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("curso", new Curso());
        model.addObject("acao", "salvar");
        model.setViewName("Cursos/formCurso");
        return model;
    }

    @PostMapping("/cursos/criar")
    public ModelAndView saveCurso(
            @Valid Curso curso,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ){
        if (validation.hasErrors()) {
            model.setViewName("Cursos/formCurso");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarCurso(curso);
        model.setViewName("redirect:/admin/cursos");
        return model;
    }

    @PostMapping("/cursos/deletar/{id}")
    public ModelAndView deletarCurso(@PathVariable(value = "id") Long id, ModelAndView model) {
        adminService.removerCurso(id);
        model.setViewName("redirect:/admin/cursos");
        return model;
    }

    // Assuntos

    @GetMapping("/assuntos")
    public ModelAndView listAssuntos(ModelAndView model) {
        model.addObject("assuntos", adminService.getAssuntos());
        model.setViewName("Assuntos/listaAssuntos");
        return model;
    }

    @GetMapping("/assuntos/criar")
    public ModelAndView createAssunto(ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("assunto", new Assunto());
        model.setViewName("Assuntos/formAssunto");
        return model;
    }

    @PostMapping("/assuntos/criar")
    public ModelAndView saveAssunto(@Valid Assunto assunto, BindingResult validation,
        ModelAndView model,
        RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            model.setViewName("Assuntos/formAssunto");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarAssunto(assunto);
        model.addObject("assuntos", adminService.getAssuntos());
        model.setViewName("redirect:/admin/assuntos");
        return model;
    }

    @PostMapping("/assuntos/deletar/{id}")
    public ModelAndView deletarAssunto(@PathVariable(value = "id") Long id, ModelAndView model) {
        adminService.removerAssunto(id);
        model.setViewName("redirect:/admin/assuntos");
        return model;
    }

    // CRUD Aluno

    @GetMapping("/alunos")
    public ModelAndView listAlunos(ModelAndView model) {
        model.addObject("alunos", adminService.getAlunos());
        model.setViewName("Alunos/listaAlunos");
        return model;
    }

    @GetMapping("/alunos/criar")
    public ModelAndView criarAluno(ModelAndView model) {
        model.addObject("aluno", new Aluno());
        model.addObject("cursos", adminService.getCursos());
        model.addObject("acao", "salvar");
        model.setViewName("Alunos/formAluno");
        return model;
    }

    @PostMapping("/alunos/criar")
    public ModelAndView saveAluno(
            @Valid Aluno aluno,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ) {
        if (validation.hasErrors()) {
            model.setViewName("Alunos/formAluno");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarAluno(aluno);
        model.addObject("alunos", adminService.getAlunos());
        model.setViewName("redirect:/admin/alunos");
        return model;
    }

    @GetMapping("/alunos/editar/{id}")
    public ModelAndView editAluno(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("aluno", adminService.getAlunoPorId(id));
        model.addObject("cursos", this.adminService.getCursos());
        model.addObject("acao", "editar");
        model.setViewName("Alunos/formAluno");
        redirectAttributes.addFlashAttribute("mensagem", "O Aluno foi Editado!");
        redirectAttributes.addFlashAttribute("O Aluno foi Editado", true);
        return model;
    }
    
    @PostMapping("/alunos/editar/{id}")
    public ModelAndView updateAluno(
            @Valid Aluno aluno,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes) {
        if (validation.hasErrors()) {
            model.setViewName("Alunos/formAluno");
            model.addObject("acao", "editar");
            return model;
        }
        adminService.atualizarAluno(aluno);
        model.addObject("alunos", adminService.getAlunos());
        model.setViewName("redirect:/admin/alunos");
        return model;
    }

    @PostMapping("/alunos/deletar/{id}")
    public ModelAndView deletarAluno(@PathVariable(value = "id") Long id, ModelAndView model) {
        adminService.removerAluno(id);
        model.setViewName("redirect:/admin/alunos");
        return model;
    }

    // CRUD Professor

    @GetMapping("/professores")
    public ModelAndView listProfessores(ModelAndView model) {
        model.addObject("professores", adminService.getProfessores());
        model.setViewName("Professor/listaProfessores");
        return model;
    }

    @GetMapping("/professores/criar")
    public ModelAndView criarProfessor(ModelAndView model) {
        model.addObject("professor", new Professor());
        model.addObject("cursos", this.adminService.getCursos());
        model.addObject("acao", "salvar");
        model.setViewName("Professor/formProfessor");
        return model;
    }

    @PostMapping("/professores/criar")
    public ModelAndView saveProfessor(
            @Valid Professor professor,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ) {
        if (validation.hasErrors()) {
            model.setViewName("Professor/formProfessor");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarProfessor(professor);
        model.addObject("professores", adminService.getProfessores());
        model.setViewName("redirect:/admin/professores");
        return model;
    }

    @GetMapping("/professores/editar/{id}")
    public ModelAndView editProfessor(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("professor", adminService.getProfessorPorId(id));
        model.addObject("cursos", this.adminService.getCursos());
        model.addObject("acao", "editar");
        model.setViewName("Professor/formProfessor");
        redirectAttributes.addFlashAttribute("mensagem", "O Professor foi Editado!");
        redirectAttributes.addFlashAttribute("O Professor foi Editado", true);
        return model;
    }

    @PostMapping("/professores/editar/{id}")
    public ModelAndView updateProfessor(
            @Valid Professor professor,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ) {
        if (validation.hasErrors()) {
            model.setViewName("Professor/formProfessor");
            model.addObject("acao", "editar");
            return model;
        }
        adminService.atualizarProfessor(professor);
        model.addObject("professores", adminService.getProfessores());
        model.setViewName("redirect:/admin/professores");
        return model;
    }

    @PostMapping("/professores/deletar/{id}")
    public ModelAndView deletarProfessor(@PathVariable(value = "id") Long id, ModelAndView model) {
        adminService.removerProfessor(id);
        model.setViewName("redirect:/admin/professores");
        return model;
    }

    // CRUD Coordenador

    @GetMapping("/coordenadores")
    public ModelAndView listCoordenadores(ModelAndView model) {
        model.addObject("coordenadores", adminService.getCoordenadores());
        model.setViewName("Coordenador/listaCoordenadores");
        return model;
    }

    @GetMapping("/professores/{cursoId}")
    @ResponseBody
    public List<Professor> getProfessorPorCurso(@PathVariable Long cursoId) {
        List<Professor> professores = professorService.getProfessoresPorCurso(cursoId);
        return professores;
    }

    @GetMapping("/coordenadores/criar") 
    public ModelAndView criarCoordenador(ModelAndView model) {
        model.addObject("coordenador", new Coordenador( new Professor(), new Curso()));
        model.addObject("cursos", this.adminService.getCursos());
        model.addObject("professores", this.adminService.getProfessores());

        model.addObject("acao", "salvar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("/coordenadores/criar")
    public ModelAndView saveCoordenador(
            @Valid Coordenador coordenador,
            BindingResult validation,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ) {
        if (validation.hasErrors()) {
            model.setViewName("Coordenador/formCoordenador");
            model.addObject("acao", "salvar");
            return model;
        }
        adminService.criarCoordenador(coordenador);
        model.addObject("coordenadores", adminService.getCoordenadores());
        model.setViewName("redirect:/admin/coordenadores");
        return model;
    }

    @GetMapping("/coordenadores/editar/{id}")
    public ModelAndView editCoordenador(@PathVariable("id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        model.addObject("coordenador", adminService.getCoordenadorPorId(id));
        model.addObject("cursos", this.adminService.getCursos());
        model.addObject("acao", "editar");
        model.setViewName("Coordenador/formCoordenador");
        return model;
    }

    @PostMapping("/coordenadores/editar/{id}")
    public ModelAndView updateCoordenador(
            @Valid Coordenador coordenador,
            BindingResult validation,
            @PathVariable("id") Long id,
            ModelAndView model,
            RedirectAttributes redirectAttributes
    ) {
        if (validation.hasErrors()) {
            model.setViewName("Coordenador/formCoordenador");
            model.addObject("acao", "editar");
            return model;
        }
        adminService.atualizarCoordenador(coordenador);
        model.addObject("coordenadores", adminService.getCoordenadores());
        model.setViewName("redirect:/admin/coordenadores");
        return model;
    }

    @PostMapping("/coordenadores/deletar/{id}")
    public ModelAndView deletarCoordenador(@PathVariable(value = "id") Long id, ModelAndView model, RedirectAttributes redirectAttributes) {
        adminService.deletarCoordenador(id);
        model.addObject("coordenadores", adminService.getCoordenadores());
        model.addObject("coordenador", new Coordenador(new Professor(), new Curso()));
        model.setViewName("redirect:/coordenadores");
        redirectAttributes.addFlashAttribute("mensagem","Coordenador Deletado com Sucesso");
        redirectAttributes.addFlashAttribute("coordenadoresDeletado", true);
        return model;
    }
}
