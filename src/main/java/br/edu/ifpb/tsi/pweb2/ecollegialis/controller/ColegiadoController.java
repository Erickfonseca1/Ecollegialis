package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Colegiado;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Professor;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ColegiadoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/colegiado")
@AllArgsConstructor
public class ColegiadoController {

    private final ColegiadoService colegiadoService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarColegiado(@RequestBody Colegiado colegiado) {
        return ResponseEntity.status(HttpStatus.CREATED).body(colegiadoService.criarColegiado(colegiado));
    }

    @PatchMapping("/professor/{idColegiado}/{idProfessor}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado adicionarProfessor(@PathVariable Long idColegiado, @PathVariable Long idProfessor){
        return colegiadoService.adicionarProfessor(idColegiado, idProfessor);
    }

    @DeleteMapping("/professor/{idColegiado}/{idProfessor}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado removerProfessor(@PathVariable int idColegiado, @PathVariable int idProfessor){
        return colegiadoService.removerProfessor(idColegiado, idProfessor);
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado buscarColegiadoPorId(@PathVariable Long id) {
        return colegiadoService.buscarPorId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarColegiado(@PathVariable Long id) {
        colegiadoService.deletarColegiado(id);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Colegiado atualizarColegiado(@Valid @RequestBody Colegiado colegiadoAtualizado, @PathVariable Long id) {
        return colegiadoService.atualizarColegiado(colegiadoAtualizado, id);
    }
}
