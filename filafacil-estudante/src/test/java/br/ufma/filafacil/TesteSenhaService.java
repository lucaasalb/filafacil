package br.ufma.filafacil;

import br.ufma.filafacil.model.PoliticaFila;
import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.StatusSenha;
import br.ufma.filafacil.model.TipoServico;
import br.ufma.filafacil.patterns.observer.PublicadorEventos;
import br.ufma.filafacil.repository.SenhaRepositorioMemoria;
import br.ufma.filafacil.repository.SenhaRepository;
import br.ufma.filafacil.service.SenhaService;

// Testes simples, sem biblioteca externa.
// Cada metodo testa uma parte do sistema e imprime se passou ou falhou.
public class TesteSenhaService {

    private static int passou = 0;
    private static int falhou = 0;

    public static void main(String[] args) {
        testeNumeracaoSequencial();
        testeFifoChamaMaisAntiga();
        testePrioridadeChamaPrioritaria();
        testeNaoFinalizaSenhaAguardando();
        testePainelConta();

        System.out.println();
        System.out.println("Resultado: " + passou + " passou, " + falhou + " falhou.");

        if (falhou > 0) {
            System.exit(1);
        }
    }

    // Cria um servico novo para cada teste, comecando do zero.
    private static SenhaService novoServico() {
        SenhaRepository repositorio = new SenhaRepositorioMemoria();
        PublicadorEventos publicador = new PublicadorEventos();
        return new SenhaService(repositorio, publicador);
    }

    private static void testeNumeracaoSequencial() {
        SenhaService servico = novoServico();
        Senha primeira = servico.criarSenha("Joao", TipoServico.GERAL, Prioridade.NORMAL);
        Senha segunda = servico.criarSenha("Maria", TipoServico.GERAL, Prioridade.NORMAL);

        verificar("Numeracao sequencial",
                primeira.getNumero() == 1 && segunda.getNumero() == 2);
    }

    private static void testeFifoChamaMaisAntiga() {
        SenhaService servico = novoServico();
        Senha primeira = servico.criarSenha("Primeiro", TipoServico.GERAL, Prioridade.NORMAL);
        servico.criarSenha("Segundo", TipoServico.GERAL, Prioridade.PRIORITARIA);

        Senha chamada = servico.chamarProxima(PoliticaFila.FIFO, "CONSOLE");

        verificar("FIFO chama a mais antiga",
                chamada.getNumero() == primeira.getNumero());
    }

    private static void testePrioridadeChamaPrioritaria() {
        SenhaService servico = novoServico();
        servico.criarSenha("Normal", TipoServico.GERAL, Prioridade.NORMAL);
        Senha prioritaria = servico.criarSenha("Idoso", TipoServico.GERAL, Prioridade.PRIORITARIA);

        Senha chamada = servico.chamarProxima(PoliticaFila.PRIORIDADE, "CONSOLE");

        verificar("Prioridade chama a prioritaria",
                chamada.getNumero() == prioritaria.getNumero());
    }

    private static void testeNaoFinalizaSenhaAguardando() {
        SenhaService servico = novoServico();
        Senha senha = servico.criarSenha("Teste", TipoServico.GERAL, Prioridade.NORMAL);

        boolean deuErro = false;
        try {
            // Nao pode finalizar sem antes chamar
            servico.finalizar(senha.getNumero());
        } catch (IllegalStateException e) {
            deuErro = true;
        }

        verificar("Nao finaliza senha que so esta aguardando", deuErro);
    }

    private static void testePainelConta() {
        SenhaService servico = novoServico();
        servico.criarSenha("A", TipoServico.GERAL, Prioridade.NORMAL);
        servico.criarSenha("B", TipoServico.GERAL, Prioridade.NORMAL);
        servico.chamarProxima(PoliticaFila.FIFO, "CONSOLE");

        var resumo = servico.gerarResumo();

        verificar("Painel conta corretamente",
                resumo.getTotal() == 2
                        && resumo.getAguardando() == 1
                        && resumo.getChamadas() == 1);
    }

    // Metodo auxiliar que registra o resultado do teste.
    private static void verificar(String nome, boolean condicao) {
        if (condicao) {
            System.out.println("[OK] " + nome);
            passou++;
        } else {
            System.out.println("[FALHOU] " + nome);
            falhou++;
        }
    }
}
