package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Reuniao;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Usuario;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProfessorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/professor")
@AllArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarProfessor(@RequestBody Professor professor){
        return ResponseEntity.status(HttpStatus.CREATED).body(professorService.criarProfessor(professor));
    }

    @GetMapping("/listar")
    public ResponseEntity<Object> listarProfessores(){
        return ResponseEntity.ok(professorService.listarProfessores());
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Object> buscarProfessorPorId(@PathVariable Long id){
        return ResponseEntity.ok(professorService.buscarProfessorPorId(id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Object> deletarProfessor(@PathVariable Long id){
        professorService.deletarProfessor(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarProfessor(@Valid @RequestBody Professor professor){
        return ResponseEntity.ok(professorService.atualizarProfessor(professor));
    }

}
