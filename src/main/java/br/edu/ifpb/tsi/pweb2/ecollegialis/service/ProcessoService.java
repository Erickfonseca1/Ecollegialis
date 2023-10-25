package br.edu.ifpb.tsi.pweb2.ecollegialis.service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifpb.tsi.pweb2.ecollegialis.repository.ProcessoRepository;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProcessoService {

        @Autowired
        ProcessoRepository processoRepository;
        AssuntoService assuntoService;
        AlunoService alunoService;
        ProfessorService professorService;

        public List<Processo> listarProcessos() {

            return processoRepository.findAll();
        }

        public Processo buscarProcessoPorId(Long id) {
            return processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
        }

        @Transactional
        public Processo criarProcesso(Processo processo) {
            var assunto = assuntoService.buscarAssuntoPorId(processo.getAssunto().getId());
            var aluno = alunoService.buscarAlunosPorId(processo.getAluno().getId());

            if (assunto != null && aluno != null) {
                processo.setAssunto(assunto);
                processo.setAluno(aluno);
                processo = processoRepository.save(processo);
                return processo;
            } else {
                throw new RuntimeException("Assunto ou aluno não encontrados");
            }
        }

        @Transactional
        public void deletarProcesso(Long id) {
            Processo processo = processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
            processoRepository.delete(processo);
        }

        @Transactional
        public Processo atualizarProcesso(Processo processoAtualizado, Long id) {
            Processo processo = processoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Processo não encontrado"));
            processo.setNumero(processoAtualizado.getNumero());
            processo.setDataRecepcao(processoAtualizado.getDataRecepcao());
            processo.setDataDistribuicao(processoAtualizado.getDataDistribuicao());
            processo.setDataParecer(processoAtualizado.getDataParecer());
            processo.setParecer(processoAtualizado.getParecer());
            processo.setStatus(processoAtualizado.getStatus());
            processo.setAssunto(processoAtualizado.getAssunto());
            processo.setVotos(processoAtualizado.getVotos());
            processo.setAluno(processoAtualizado.getAluno());
            processo.setProfessor(processoAtualizado.getProfessor());
            return processoRepository.save(processo);
        }


        public Processo mudarDecisao(int id, TipoDecisao novaDecisao){
            Optional<Processo> processoOptional = this.processoRepository.findById((long) id);
            if (processoOptional.isPresent()) {
                Processo processo = processoOptional.get();
                processo.setParecer(novaDecisao);
                return this.processoRepository.save(processo);
            } else {
                throw new RuntimeException("Processo não encontrado");
            }
        }

        public List<Processo> listarProcessosCoordenador() {
            return processoRepository.findAllByStatusAndAlunoIdAndProfessorId(StatusProcesso.CRIADO, null, null);
        }




}