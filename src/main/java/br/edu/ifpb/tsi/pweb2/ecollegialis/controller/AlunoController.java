package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.enums.StatusEnum;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AssuntoRepository;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/aluno")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/salvar-aluno")
    public String salvarAluno(@Valid Aluno aluno, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println(result.getAllErrors());
            return "Aluno/formAluno";
        }
        if (aluno.getId() != null) {
            alunoService.update(aluno);
        } else {
            alunoService.save(aluno);
        }

        return "redirect:/aluno/list";
    }

    @GetMapping("/new")
    public ModelAndView showForm() {
        ModelAndView mv = new ModelAndView("Aluno/formAluno");
        mv.addObject("aluno", new Aluno());
        return mv;
    }

    @GetMapping("/list")
    public ModelAndView listaAlunos() {
        List<Aluno> alunos = alunoService.findAll();
        ModelAndView modelAndView = new ModelAndView("Aluno/listaAlunos");
        modelAndView.addObject("alunos", alunos);
        return modelAndView;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAluno(@PathVariable(value = "id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(alunoService.findById(id));
    }

    @GetMapping("listarprocesso/{id}")
    public ModelAndView listarprocessoAluno(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("alunos/listarprocessoaluno");
        Aluno aluno = alunoService.findById(id);
        mv.addObject("processos", aluno.getProcessos());
        mv.addObject("statusProcesso", StatusEnum.values());
        mv.addObject("Aluno", aluno);

        return mv;
    }

    @GetMapping("/new")
    public ModelAndView current(ModelAndView modelAndView, Aluno aluno) {
        modelAndView.setViewName("alunos/new");
        modelAndView.addObject("aluno", aluno);

        return modelAndView;
    }

    @GetMapping("/{id}/edit")
    public ModelAndView edit(@PathVariable Long id, ModelAndView modelAndView, RedirectAttributes attr) {
        try {
            Aluno aluno = alunoService.findById(id);

            var request = new Aluno();

            modelAndView.setViewName("alunos/edit");
            modelAndView.addObject("alunoId", aluno.getId());
            modelAndView.addObject("aluno", request);

        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/alunos");
        }

        return modelAndView;
    }

    @GetMapping("/{id}/delete")
    public ModelAndView deletarAluno(ModelAndView modelAndView, @PathVariable (value = "id") Long id, RedirectAttributes attr){
        try {
            var alunoExistente = alunoService.findById(id);
            alunoService.delete(alunoExistente.getId());
            attr.addFlashAttribute("message", "OK: Aluno excluído com sucesso!");
            attr.addFlashAttribute("error", "false");
            modelAndView.setViewName("redirect:/alunos");
        } catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            modelAndView.setViewName("redirect:/alunos");
        }

        return modelAndView;
    }

    @GetMapping("/cadastrarprocesso/{id}")
    public ModelAndView cadastrarprocesso(@PathVariable Long id, RedirectAttributes attr, HttpSession session){
        ModelAndView mv = new ModelAndView("redirect:/assuntos/new");
        try{
            Aluno aluno = alunoService.findById(id);
            session.setAttribute("aluno", aluno);
            System.out.println("oiiii");
            System.out.println(aluno);




        }catch (Exception e) {
            attr.addFlashAttribute("message", "Error: "+e);
            attr.addFlashAttribute("error", "true");
            mv.setViewName("redirect:/alunos");
        }
        return mv;
    }

    @PostMapping("/cadastrarprocesso/{id}")
    public ModelAndView cadastrarProcessoAluno (@PathVariable Long id){

        ModelAndView mv = new ModelAndView("alunos/index");

        return mv;
    }
}
