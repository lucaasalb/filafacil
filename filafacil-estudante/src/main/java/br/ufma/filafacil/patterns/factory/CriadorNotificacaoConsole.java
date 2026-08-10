package br.ufma.filafacil.patterns.factory;

// FACTORY METHOD - criador concreto que produz notificacoes de console.
public class CriadorNotificacaoConsole extends CriadorNotificacao {

    @Override
    protected Notificacao criarNotificacao() {
        return new NotificacaoConsole();
    }
}
