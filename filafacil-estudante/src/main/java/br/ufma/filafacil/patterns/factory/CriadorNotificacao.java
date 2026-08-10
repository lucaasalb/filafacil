package br.ufma.filafacil.patterns.factory;

import br.ufma.filafacil.model.Senha;

// FACTORY METHOD - classe abstrata que define o "molde".
// O metodo criarNotificacao() e o factory method: cada subclasse decide
// qual notificacao concreta sera criada. O metodo notificarChamada()
// usa esse produto sem saber qual e o tipo exato.
public abstract class CriadorNotificacao {

    // Este e o factory method que as subclasses devem implementar.
    protected abstract Notificacao criarNotificacao();

    public void notificarChamada(Senha senha) {
        Notificacao notificacao = criarNotificacao();
        String mensagem = "Senha " + senha.getNumero() + " chamada para atendimento.";
        notificacao.enviar(senha, mensagem);
    }
}
