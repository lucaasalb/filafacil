package br.ufma.filafacil.patterns.factory;

// FACTORY METHOD - criador concreto que produz notificacoes de email.
public class CriadorNotificacaoEmail extends CriadorNotificacao {

    @Override
    protected Notificacao criarNotificacao() {
        return new NotificacaoEmail();
    }
}
