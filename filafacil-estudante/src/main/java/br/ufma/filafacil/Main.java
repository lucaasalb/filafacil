package br.ufma.filafacil;

import br.ufma.filafacil.model.Prioridade;
import br.ufma.filafacil.model.TipoServico;
import br.ufma.filafacil.patterns.observer.ObservadorAuditoria;
import br.ufma.filafacil.patterns.observer.ObservadorPainel;
import br.ufma.filafacil.patterns.observer.PublicadorEventos;
import br.ufma.filafacil.repository.SenhaRepositorioMemoria;
import br.ufma.filafacil.repository.SenhaRepository;
import br.ufma.filafacil.service.SenhaService;
import br.ufma.filafacil.web.ServidorWeb;


public class Main {

    public static void main(String[] args) throws Exception {
        // cria o repositorio em memoria
        SenhaRepository repositorio = new SenhaRepositorioMemoria();

        // criar publicador e inscrever
        PublicadorEventos publicador = new PublicadorEventos();
        publicador.inscrever(new ObservadorAuditoria());
        publicador.inscrever(new ObservadorPainel());

        // cria o serviço
        SenhaService servico = new SenhaService(repositorio, publicador);

        // Cria algumas senhas de exemplo para facilitar os testes na tela
        servico.criarSenha("Lucas Albuquerque", TipoServico.FINANCEIRO, Prioridade.NORMAL);
        servico.criarSenha("Ana Luiza", TipoServico.TECNICO, Prioridade.PRIORITARIA);
        servico.criarSenha("Antonio Lima", TipoServico.GERAL, Prioridade.NORMAL);
        servico.criarSenha("Filipe" , TipoServico.GERAL, Prioridade.NORMAL);

        //porta
        int porta = 8080;
        ServidorWeb servidor = new ServidorWeb(porta, servico);
        servidor.iniciar();

        System.out.println("FilaFacil iniciado em http://localhost:" + porta);
    }
}
