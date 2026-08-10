package br.ufma.filafacil.patterns.strategy;

import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.StatusSenha;

import java.util.List;

// STRATEGY - implementacao PRIORIDADE.
// Primeiro tenta achar uma senha prioritaria aguardando.
// Se nao houver nenhuma, cai para a ordem normal (FIFO).
public class EstrategiaPrioridade implements EstrategiaFila {

    @Override
    public Senha escolherProxima(List<Senha> senhas) {
        Senha prioritaria = null;
        Senha normal = null;

        for (Senha senha : senhas) {
            if (senha.getStatus() != StatusSenha.AGUARDANDO) {
                continue;
            }

            if (senha.getPrioridade() == Prioridade.PRIORITARIA) {
                if (prioritaria == null || senha.getNumero() < prioritaria.getNumero()) {
                    prioritaria = senha;
                }
            } else {
                if (normal == null || senha.getNumero() < normal.getNumero()) {
                    normal = senha;
                }
            }
        }

        // Se existe prioritaria, ela vem primeiro. Senao, retorna a normal mais antiga.
        if (prioritaria != null) {
            return prioritaria;
        }
        return normal;
    }
}
