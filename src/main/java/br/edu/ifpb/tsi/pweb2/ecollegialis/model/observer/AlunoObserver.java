package br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer;
import org.springframework.stereotype.Component;
import br.edu.ifpb.tsi.pweb2.ecollegialis.model.Aluno;

@Component
public class AlunoObserver extends AbstractObserver {

    private String lastMessage;
    private Aluno aluno;

    public AlunoObserver(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public void update(String message) {
        // Lógica específica para o AlunoObserver ao receber uma notificação do Subject
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
