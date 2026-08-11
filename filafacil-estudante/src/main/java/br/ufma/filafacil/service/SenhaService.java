package br.ufma.filafacil.service;

import br.ufma.filafacil.model.PoliticaFila;
import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.model.StatusSenha;
import br.ufma.filafacil.model.TipoServico;
import br.ufma.filafacil.patterns.factory.CriadorNotificacao;
import br.ufma.filafacil.patterns.factory.CriadorNotificacaoConsole;
import br.ufma.filafacil.patterns.factory.CriadorNotificacaoEmail;
import br.ufma.filafacil.patterns.observer.PublicadorEventos;
import br.ufma.filafacil.patterns.strategy.EstrategiaFifo;
import br.ufma.filafacil.patterns.strategy.EstrategiaFila;
import br.ufma.filafacil.patterns.strategy.EstrategiaPrioridade;
import br.ufma.filafacil.repository.SenhaRepository;

import java.util.List;

import br.ufma.filafacil.patterns.observer.ObservadorMetricas;

// Camada de servico: onde ficam os casos de uso do sistema.
// Ela usa o repositorio para guardar os dados e os tres padroes
// (Strategy, Observer e Factory Method) para resolver o problema.
public class SenhaService {

    private SenhaRepository repositorio;
    private PublicadorEventos publicador;
    private ObservadorMetricas observadorMetricas;

    public SenhaService(SenhaRepository repositorio, PublicadorEventos publicador) {
        this(repositorio, publicador, null);
    }

    public SenhaService(SenhaRepository repositorio, PublicadorEventos publicador, ObservadorMetricas observadorMetricas) {
        this.repositorio = repositorio;
        this.publicador = publicador;
        this.observadorMetricas = observadorMetricas;
    }

    // Cria uma nova senha e avisa os observadores.
    public Senha criarSenha(String nomeCliente, TipoServico tipoServico, Prioridade prioridade) {
        int numero = repositorio.proximoNumero();
        Senha senha = new Senha(numero, nomeCliente, tipoServico, prioridade);
        repositorio.salvar(senha);
        publicador.notificar("CRIADA", senha);
        return senha;
    }

    public List<Senha> listarSenhas() {
        return repositorio.listarTodas();
    }

    // Chama a proxima senha usando a politica escolhida (Strategy)
    // e notifica o cliente usando o canal escolhido (Factory Method).
    public Senha chamarProxima(PoliticaFila politica, String canalNotificacao) {
        EstrategiaFila estrategia = escolherEstrategia(politica);
        Senha senha = estrategia.escolherProxima(repositorio.listarTodas());

        if (senha == null) {
            throw new IllegalStateException("Nao ha senhas aguardando atendimento.");
        }

        senha.chamar();
        repositorio.salvar(senha);
        publicador.notificar("CHAMADA", senha);

        CriadorNotificacao criador = escolherCriador(canalNotificacao);
        criador.notificarChamada(senha);

        return senha;
    }

    public Senha finalizar(int numero) {
        Senha senha = buscarOuFalhar(numero);
        senha.finalizar();
        repositorio.salvar(senha);
        publicador.notificar("FINALIZADA", senha);
        return senha;
    }

    public Senha cancelar(int numero) {
        Senha senha = buscarOuFalhar(numero);
        senha.cancelar();
        repositorio.salvar(senha);
        publicador.notificar("CANCELADA", senha);
        return senha;
    }

    // Monta o resumo do painel contando as senhas em cada status.
    public ResumoPainel gerarResumo() {
        List<Senha> senhas = repositorio.listarTodas();
        int aguardando = 0;
        int chamadas = 0;
        int finalizadas = 0;
        int canceladas = 0;

        for (Senha senha : senhas) {
            if (senha.getStatus() == StatusSenha.AGUARDANDO) {
                aguardando++;
            } else if (senha.getStatus() == StatusSenha.CHAMADA) {
                chamadas++;
            } else if (senha.getStatus() == StatusSenha.FINALIZADA) {
                finalizadas++;
            } else if (senha.getStatus() == StatusSenha.CANCELADA) {
                canceladas++;
            }
        }

        return new ResumoPainel(senhas.size(), aguardando, chamadas, finalizadas, canceladas);
    }

    public EstatisticasAtendimento gerarEstatisticas() {
        if (observadorMetricas != null) {
            return observadorMetricas.gerarEstatisticas();
        }
        return new EstatisticasAtendimento(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }

    // Escolhe qual estrategia (Strategy) usar conforme a politica.
    private EstrategiaFila escolherEstrategia(PoliticaFila politica) {
        if (politica == PoliticaFila.PRIORIDADE) {
            return new EstrategiaPrioridade();
        }
        return new EstrategiaFifo();
    }

    // Escolhe qual criador de notificacao (Factory Method) usar.
    private CriadorNotificacao escolherCriador(String canal) {
        if (canal != null && canal.equalsIgnoreCase("EMAIL")) {
            return new CriadorNotificacaoEmail();
        }
        return new CriadorNotificacaoConsole();
    }

    private Senha buscarOuFalhar(int numero) {
        Senha senha = repositorio.buscarPorNumero(numero);
        if (senha == null) {
            throw new IllegalArgumentException("Senha nao encontrada: " + numero);
        }
        return senha;
    }
}
