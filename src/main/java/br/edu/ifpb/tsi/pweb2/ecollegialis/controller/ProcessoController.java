package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.ProcessoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping("/criar")
    public ResponseEntity<Object> criarProcesso(@RequestBody Processo processo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(processoService.criarProcesso(processo));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Processo>> listarProcessos() {
        return ResponseEntity.ok(processoService.listarProcessos());
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Processo> listarProcesso(@PathVariable Long id) {
        return ResponseEntity.ok(processoService.buscarProcessoPorId(id));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> deletarProcesso(@PathVariable Long id) {
        processoService.deletarProcesso(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Processo> atualizarProcesso(@RequestBody Processo processoAtualizado, @PathVariable Long id) {
        return ResponseEntity.ok(processoService.atualizarProcesso(processoAtualizado, id));
    }

}
