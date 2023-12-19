package br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer;

import org.springframework.stereotype.Component;

@Component
public class AlunoObserver extends AbstractObserver {

    private String lastMessage;

    @Override
    public void update(String message) {
        // Lógica específica para o AlunoObserver ao receber uma notificação do Subject
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
