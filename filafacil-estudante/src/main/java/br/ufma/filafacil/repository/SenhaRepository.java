package br.ufma.filafacil.repository;

import br.ufma.filafacil.model.Senha;

import java.util.List;

// Contrato de armazenamento das senhas.
// Deixamos como interface para que a forma de guardar (memoria, banco, etc.)
// possa ser trocada depois sem mexer no restante do sistema.
public interface SenhaRepository {

    void salvar(Senha senha);

    List<Senha> listarTodas();

    Senha buscarPorNumero(int numero);

    int proximoNumero();
}
