package br.ufma.filafacil.repository;

import br.ufma.filafacil.model.Senha;

import java.util.ArrayList;
import java.util.List;

// Guarda as senhas em uma lista na memoria.
// Simples de rodar, mas os dados somem quando o programa fecha.
public class SenhaRepositorioMemoria implements SenhaRepository {

    private List<Senha> senhas = new ArrayList<>();
    private int contador = 0;

    @Override
    public void salvar(Senha senha) {
        // Se a senha ainda nao esta na lista, adiciona.
        // Como usamos o mesmo objeto na memoria, as alteracoes de status
        // ja ficam refletidas sem precisar substituir nada.
        if (!senhas.contains(senha)) {
            senhas.add(senha);
        }
    }

    @Override
    public List<Senha> listarTodas() {
        // Retorna uma copia para o resto do sistema nao mexer na lista interna.
        return new ArrayList<>(senhas);
    }

    @Override
    public Senha buscarPorNumero(int numero) {
        for (Senha senha : senhas) {
            if (senha.getNumero() == numero) {
                return senha;
            }
        }
        return null;
    }

    @Override
    public int proximoNumero() {
        contador = contador + 1;
        return contador;
    }
}
