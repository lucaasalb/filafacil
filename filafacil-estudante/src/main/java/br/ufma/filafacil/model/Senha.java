package br.ufma.filafacil.model;

import java.time.LocalDateTime;

// Entidade principal do sistema: representa uma senha de atendimento.
// Guarda os dados do cliente e controla em que estado a senha esta.
public class Senha {

    private int numero;
    private String nomeCliente;
    private TipoServico tipoServico;
    private Prioridade prioridade;
    private StatusSenha status;
    private LocalDateTime criadaEm;
    private LocalDateTime chamadaEm;
    private LocalDateTime finalizadaEm;

    public Senha(int numero, String nomeCliente, TipoServico tipoServico, Prioridade prioridade) {
        if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do cliente e obrigatorio.");
        }
        this.numero = numero;
        this.nomeCliente = nomeCliente.trim();
        this.tipoServico = tipoServico;
        this.prioridade = prioridade;
        this.status = StatusSenha.AGUARDANDO;
        this.criadaEm = LocalDateTime.now();
    }

    // Marca a senha como chamada. So pode chamar uma senha que esta aguardando.
    public void chamar() {
        if (status != StatusSenha.AGUARDANDO) {
            throw new IllegalStateException("So e possivel chamar senhas que estao aguardando.");
        }
        status = StatusSenha.CHAMADA;
        chamadaEm = LocalDateTime.now();
    }

    // Finaliza o atendimento. So pode finalizar uma senha que ja foi chamada.
    public void finalizar() {
        if (status != StatusSenha.CHAMADA) {
            throw new IllegalStateException("So e possivel finalizar senhas que foram chamadas.");
        }
        status = StatusSenha.FINALIZADA;
        finalizadaEm = LocalDateTime.now();
    }

    // Cancela a senha. Nao pode cancelar uma senha ja finalizada ou cancelada.
    public void cancelar() {
        if (status == StatusSenha.FINALIZADA || status == StatusSenha.CANCELADA) {
            throw new IllegalStateException("Nao e possivel cancelar uma senha ja encerrada.");
        }
        status = StatusSenha.CANCELADA;
        finalizadaEm = LocalDateTime.now();
    }

    public int getNumero() {
        return numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public TipoServico getTipoServico() {
        return tipoServico;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public StatusSenha getStatus() {
        return status;
    }

    public LocalDateTime getCriadaEm() {
        return criadaEm;
    }

    public LocalDateTime getChamadaEm() {
        return chamadaEm;
    }

    public LocalDateTime getFinalizadaEm() {
        return finalizadaEm;
    }
}
