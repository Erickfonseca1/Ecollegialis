package br.edu.ifpb.tsi.pweb2.ecollegialis.controllers;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Assunto;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.services.AlunoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.services.AssuntoService;
import br.edu.ifpb.tsi.pweb2.ecollegialis.services.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    private final ProcessoService processoService;
    private final AlunoService alunoService; // Se você ainda não criou, pode criar um serviço de Aluno
    private final AssuntoService assuntoService;

    @Autowired
    public ProcessoController(ProcessoService processoService, AlunoService alunoService, AssuntoService assuntoService) {
        this.processoService = processoService;
        this.alunoService = alunoService;
        this.assuntoService = assuntoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Processo> cadastrarProcesso(
            @RequestParam Long alunoId, // Você pode usar outros métodos de autenticação/identificação do aluno
            @RequestParam Long assuntoId,
            @RequestParam String textoRequerimento
    ) {
        Aluno aluno = alunoService.obterAlunoPorId(alunoId); // Implemente um método no serviço de Aluno
        Assunto assunto = assuntoService.obterAssuntoPorId(assuntoId);
        Processo novoProcesso = processoService.cadastrarNovoProcesso(aluno, assunto, textoRequerimento);

        return new ResponseEntity<>(novoProcesso, HttpStatus.CREATED);
    }
}
