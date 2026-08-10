package br.ufma.filafacil.web;

import br.ufma.filafacil.model.Senha;
import br.ufma.filafacil.service.ResumoPainel;

import java.util.List;

// Classe auxiliar para transformar objetos em texto JSON.
// Fazemos "na mao" porque nao usamos bibliotecas externas no projeto.
public class JsonHelper {

    // Converte uma senha em JSON.
    public static String senhaParaJson(Senha senha) {
        String chamadaEm = "null";
        if (senha.getChamadaEm() != null) {
            chamadaEm = "\"" + senha.getChamadaEm() + "\"";
        }

        return "{"
                + "\"numero\":" + senha.getNumero() + ","
                + "\"nomeCliente\":\"" + senha.getNomeCliente() + "\","
                + "\"tipoServico\":\"" + senha.getTipoServico() + "\","
                + "\"prioridade\":\"" + senha.getPrioridade() + "\","
                + "\"status\":\"" + senha.getStatus() + "\","
                + "\"criadaEm\":\"" + senha.getCriadaEm() + "\","
                + "\"chamadaEm\":" + chamadaEm
                + "}";
    }

    // Converte uma lista de senhas em um array JSON.
    public static String listaParaJson(List<Senha> senhas) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < senhas.size(); i++) {
            sb.append(senhaParaJson(senhas.get(i)));
            if (i < senhas.size() - 1) {
                sb.append(",");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    // Converte o resumo do painel em JSON.
    public static String resumoParaJson(ResumoPainel resumo) {
        return "{"
                + "\"total\":" + resumo.getTotal() + ","
                + "\"aguardando\":" + resumo.getAguardando() + ","
                + "\"chamadas\":" + resumo.getChamadas() + ","
                + "\"finalizadas\":" + resumo.getFinalizadas() + ","
                + "\"canceladas\":" + resumo.getCanceladas()
                + "}";
    }

    // Mensagem simples de erro/aviso em JSON.
    public static String mensagem(String texto) {
        return "{\"mensagem\":\"" + texto + "\"}";
    }
}
