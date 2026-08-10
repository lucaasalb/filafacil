package br.ufma.filafacil.web;

import br.ufma.filafacil.service.SenhaService;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

// Sobe o servidor HTTP e registra os caminhos (rotas) da aplicacao.
public class ServidorWeb {

    private int porta;
    private SenhaService servico;

    public ServidorWeb(int porta, SenhaService servico) {
        this.porta = porta;
        this.servico = servico;
    }

    public void iniciar() throws IOException {
        HttpServer servidor = HttpServer.create(new InetSocketAddress(porta), 0);

        // Rotas da API
        servidor.createContext("/api", new ApiHandler(servico));

        // Arquivos da interface (HTML, CSS, JS)
        servidor.createContext("/", new ArquivoEstaticoHandler());

        servidor.start();
    }
}
