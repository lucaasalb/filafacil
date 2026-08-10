package br.ufma.filafacil.patterns.factory;

import br.ufma.filafacil.model.Senha;

// FACTORY METHOD - produto concreto: notificacao por console.
public class NotificacaoConsole implements Notificacao {

    @Override
    public void enviar(Senha senha, String mensagem) {
        System.out.println("[CONSOLE] " + mensagem
                + " Cliente: " + senha.getNomeCliente());
    }
}
