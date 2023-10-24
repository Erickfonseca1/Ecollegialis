package br.edu.ifpb.tsi.pweb2.ecollegialis.controller;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    @Autowired
    private AlunoService alunoService;

    @PostMapping("/cadastrar-processo/{idAluno}")
    public void cadastrarNovoProcesso(@RequestBody Processo processo, @PathVariable Long idAluno) {
        alunoService.cadastrarNovoProcesso(processo, idAluno);
    }

    @PostMapping("/adicionar-anexo")
    public void adicionarAnexo(@RequestBody Processo processo, @RequestBody byte[] anexo) {
        alunoService.adicionarAnexo(processo, anexo);
    }

    @GetMapping("/listar")
    public List<Aluno> listarAlunos() {
        return alunoService.listarAlunos();
    }

    @GetMapping("/{id}")
    public Aluno buscarAlunoPorId(@PathVariable Long id) {
        return alunoService.buscarAlunosPorId(id);
    }

    @PostMapping("/criar")
    public ResponseEntity<Object> criarAluno(@RequestBody Aluno aluno) {
        return ResponseEntity.status(HttpStatus.CREATED).body(alunoService.criarAluno(aluno));
    }

    @DeleteMapping("/remover/{id}")
    public void removerAluno(@PathVariable Long id) {
        alunoService.removerAluno(id);
    }

    @PutMapping("/atualizar")
    public Aluno atualizarAluno(@RequestBody Aluno alunoAtualizado) {
        return alunoService.atualizarAluno(alunoAtualizado);
    }
}