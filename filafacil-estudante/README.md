# FilaFácil

Projeto final da disciplina de Arquitetura de Software.

O **FilaFácil** é um sistema simples para organizar filas de atendimento. Ele permite gerar senhas, escolher a próxima senha a ser chamada de acordo com uma política (ordem de chegada ou prioridade), acompanhar o estado das senhas e ver um painel com os totais.

O sistema foi feito em **Java 21, sem bibliotecas externas**, para ficar fácil de rodar e para deixar as decisões de arquitetura visíveis no código.

## O que o sistema faz

- gerar senhas informando nome, tipo de serviço e prioridade;
- listar as senhas e seus estados;
- chamar a próxima senha por FIFO (ordem de chegada) ou por prioridade;
- finalizar e cancelar senhas;
- mostrar um painel com o total de senhas em cada situação;
- registrar os eventos no console (auditoria e painel);
- notificar a chamada por console ou por e-mail (simulado).

## Arquitetura

O projeto usa **arquitetura em camadas**:

- **model**: as entidades e regras do domínio (`Senha`, enums);
- **repository**: guarda as senhas (aqui, em memória);
- **service**: os casos de uso, onde as regras se juntam;
- **web**: o servidor HTTP, a API e os arquivos da interface;
- **patterns**: os três padrões de projeto usados.

## Padrões de projeto

- **Strategy**: escolha da próxima senha (`EstrategiaFifo` e `EstrategiaPrioridade`);
- **Observer**: eventos das senhas (`ObservadorAuditoria`, `ObservadorPainel` e `ObservadorMetricas`);
- **Factory Method**: criação das notificações (`CriadorNotificacaoConsole` e `CriadorNotificacaoEmail`).

## Novas telas desenvolvidas

- **Visão do Cliente (TV):** `http://localhost:8080/cliente.html` (Exibe chamada em tempo real na sala de espera com nome do paciente, senha, prioridade e alerta sonoro/visual).
- **Painel de Métricas:** `http://localhost:8080/metricas.html` (Dashboard com tempo médio de espera, tempo de atendimento e volume por tipo de serviço).

## Como executar

É necessário ter o **JDK 21** instalado.

### Linux ou macOS

```bash
chmod +x run.sh test.sh
./run.sh
```

### Windows

```bat
run.bat
```

Depois, abra `http://localhost:8080` no navegador.

O sistema já começa com três senhas de exemplo.

## Como rodar os testes

### Linux ou macOS

```bash
./test.sh
```

### Windows

```bat
test.bat
```

## Endpoints da API

| Método | Caminho | O que faz |
|--------|---------|-----------|
| GET | `/api/senhas` | lista as senhas |
| POST | `/api/senhas` | cria uma senha |
| POST | `/api/senhas/proxima` | chama a próxima senha |
| POST | `/api/senhas/{numero}/finalizar` | finaliza uma senha |
| POST | `/api/senhas/{numero}/cancelar` | cancela uma senha |
| GET | `/api/painel` | mostra os totais do painel |
| GET | `/api/metricas` | mostra as estatísticas e tempo médio de espera |

Exemplo de criação de senha:

```bash
curl -X POST http://localhost:8080/api/senhas \
  -H "Content-Type: application/json" \
  -d '{"nomeCliente":"Maria Silva","tipoServico":"FINANCEIRO","prioridade":"PRIORITARIA"}'
```

## Documentação

- [C4 - Nível 1 (Contexto)](docs/architecture/c4-contexto.md)
- [C4 - Nível 2 (Container)](docs/architecture/c4-container.md)
- [ADR 001 - Arquitetura em camadas](docs/adr/001-arquitetura-em-camadas.md)
- [ADR 002 - Persistência em memória](docs/adr/002-persistencia-em-memoria.md)
- [ADR 003 - Padrões de projeto](docs/adr/003-padroes-de-projeto.md)

## Limitações

As senhas ficam guardadas apenas na memória, então os dados somem quando o programa é encerrado. Não há banco de dados, login ou envio real de e-mail. Essas escolhas estão explicadas nos ADRs e poderiam ser evoluções futuras do projeto.
