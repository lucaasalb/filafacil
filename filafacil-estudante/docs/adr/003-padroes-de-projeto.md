# ADR 003 - Usar Strategy, Observer e Factory Method

- **Status:** aceito
- **Data:** 2026-08-04

## Contexto

O trabalho permite implementar ate 3 padroes de projeto, desde que facam sentido
para o problema. Precisavamos de padroes que resolvessem necessidades reais do
sistema, e nao apenas classes soltas para "cumprir tabela". Durante a evolucao do
projeto foram adicionadas novas funcionalidades, como a Visao do Cliente, o
Painel de Metricas e a Reativacao de Senhas, sem alterar as decisoes
arquiteturais inicialmente adotadas.

## Decisao

Escolhemos tres padroes, cada um para uma necessidade:

### Strategy

O sistema precisa chamar a proxima senha de formas diferentes (ordem de chegada
ou prioridade). Criamos a interface `EstrategiaFila` com as implementacoes
`EstrategiaFifo` e `EstrategiaPrioridade`. Assim da para adicionar novas politicas
sem mexer no service.

### Observer

Quando uma senha muda de estado, varias partes do sistema precisam reagir
automaticamente, como o registro de auditoria, a atualizacao do painel e a
coleta de metricas. Usamos `PublicadorEventos` para avisar os observadores
(`ObservadorAuditoria`, `ObservadorPainel` e `ObservadorMetricas`) sem que o
service conheca cada um deles, mantendo baixo acoplamento e facilitando a
adicao de novos observadores.

### Factory Method

A notificacao pode ser por console ou por e-mail. A classe abstrata
`CriadorNotificacao` define o metodo `criarNotificacao()`, e cada subclasse decide
qual notificacao criar. Isso separa a criacao do uso da notificacao.

## Consequencias

### Pontos positivos

- da para adicionar novas politicas, observadores ou notificacoes sem mexer no
  codigo que ja existe;
- os padroes aparecem no funcionamento real do sistema;
- foi possivel adicionar o painel de metricas utilizando apenas um novo
  observador, sem alterar a logica principal do sistema;
- as novas funcionalidades foram incorporadas mantendo a mesma arquitetura e
  os mesmos padroes de projeto.

### Pontos negativos

- aumenta o numero de classes;
- exige entender o papel de cada padrao para ler o codigo.