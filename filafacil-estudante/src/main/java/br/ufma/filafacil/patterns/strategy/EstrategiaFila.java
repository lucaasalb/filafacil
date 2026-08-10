package br.ufma.filafacil.patterns.strategy;

import br.ufma.filafacil.model.Senha;

import java.util.List;

// PADRAO STRATEGY
// Interface comum para as diferentes formas de escolher a proxima senha.
// Cada politica de fila e uma implementacao desta interface.
public interface EstrategiaFila {

    // Recebe a lista de senhas e devolve a proxima a ser chamada.
    // Retorna null se nao houver nenhuma senha aguardando.
    Senha escolherProxima(List<Senha> senhas);
}
