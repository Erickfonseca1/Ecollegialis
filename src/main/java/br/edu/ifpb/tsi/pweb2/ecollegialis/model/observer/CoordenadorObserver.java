package br.edu.ifpb.tsi.pweb2.ecollegialis.model.observer;

import org.springframework.stereotype.Component;

@Component
public class CoordenadorObserver extends AbstractObserver {

    private String lastMessage;

    @Override
    public void update(String message) {
        // Lógica específica para o CoordenadorObserver ao receber uma notificação do Subject
        lastMessage = message;
    }

    public String getLastMessage() {
        return lastMessage;
    }
}
