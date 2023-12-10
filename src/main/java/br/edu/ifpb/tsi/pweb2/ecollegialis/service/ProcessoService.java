package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Processo;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private AssuntoService assuntoService;

    @Autowired
    private AlunoService alunoService;

    public void save(Processo processo) {
        var assunto = processo.getAssunto();
        var aluno = processo.getAlunoProcesso();

        // Verificar se o assunto e o aluno são válidos
        if (assunto != null && aluno != null) {
            // Verificar se o assunto e o aluno estão associados a este processo
            if (assunto.getProcessos().contains(processo) && aluno.getProcessos().contains(processo)) {
                // Salvar o processo
                processoRepository.save(processo);
            } else {
                // Caso contrário, lançar uma exceção ou tratar o erro de alguma forma
                throw new IllegalArgumentException("O assunto e o aluno não estão associados a este processo.");
            }
        } else {
            // Caso contrário, lançar uma exceção ou tratar o erro de alguma forma
            throw new IllegalArgumentException("O assunto e o aluno não podem ser nulos.");
        }
    }

    public void update(Processo processo) {
        processoRepository.save(processo);
    }

    public Processo findById(Long id) {
        return processoRepository.findById(id).get();
    }

    public List<Processo> findAll() {
        return processoRepository.findAll();
    }
}
