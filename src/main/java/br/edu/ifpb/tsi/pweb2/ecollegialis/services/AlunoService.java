package br.edu.ifpb.tsi.pweb2.ecollegialis.services;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;
import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    @Autowired
    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public Aluno cadastrarAluno(Aluno aluno) {
        // Implemente a lógica necessária para cadastrar um novo aluno, como validações e outros processamentos.
        // Por exemplo:
//        if (alunoRepository.getMatricula() == null || aluno.getNome() == null) {
//            throw new IllegalArgumentException("Matrícula e nome são campos obrigatórios.");
//        }

        return alunoRepository.save(aluno);
    }

    public Aluno obterAlunoPorId(Long id) {
        return alunoRepository.findById(id).orElse(null);
    }
}

