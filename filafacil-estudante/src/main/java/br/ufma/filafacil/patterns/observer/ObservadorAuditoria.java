package br.ufma.filafacil.patterns.observer;

import br.ufma.filafacil.model.Senha;

// OBSERVER - observador que registra no console tudo que acontece.
// Serve como um "log" simples de auditoria do sistema.
public class ObservadorAuditoria implements ObservadorSenha {

    @Override
    public void aoOcorrerEvento(String tipoEvento, Senha senha) {
        System.out.println("[AUDITORIA] Evento: " + tipoEvento
                + " | Senha: " + senha.getNumero()
                + " | Status: " + senha.getStatus());
    }
}
