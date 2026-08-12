# 🏥 FilaFácil — Sistema de Gestão de Filas de Atendimento

Bem-vindo ao repositório do **FilaFácil**, projeto final da disciplina de **Arquitetura de Software** (UFMA).

O **FilaFácil** é um sistema para organização e gestão de filas de atendimento (bancos, clínicas, hospitais, cartórios e órgãos públicos). O projeto permite gerar senhas, chamar atendimentos utilizando diferentes políticas de seleção, acompanhar o status das senhas em tempo real e disponibilizar painéis específicos para operadores, clientes e gestores.

> ⚡ **Destaque:** Desenvolvido em **Java 21**, sem frameworks externos. O servidor HTTP, API REST, persistência, interface web e testes automatizados foram implementados utilizando apenas recursos nativos da linguagem, permitindo evidenciar claramente as decisões arquiteturais e os padrões de projeto adotados.

---

# 📁 Estrutura do Repositório

```text
filafacil/
├── filafacil-estudante/
│   ├── docs/
│   │   ├── adr/
│   │   ├── architecture/
│   │   ├── images/
│   │   ├── defesa.md
│   │   └── defesa.pdf
│   │
│   ├── src/
│   │   ├── main/java/
│   │   ├── main/resources/
│   │   └── test/java/
│   │
│   ├── run.bat
│   ├── run.sh
│   ├── test.bat
│   ├── test.sh
│   └── README.md
│
└── README.md
```

---

# 🏛️ Arquitetura

O sistema segue uma **Arquitetura em Camadas (Layered Architecture)** composta por:

- **Web:** Servidor HTTP e API REST.
- **Service:** Regras de negócio.
- **Model:** Entidades do domínio.
- **Repository:** Persistência em memória.

Essa divisão reduz o acoplamento entre componentes e facilita a evolução do sistema.

---

# 🎯 Padrões de Projeto

O projeto utiliza os seguintes padrões:

## Strategy

Permite alterar dinamicamente a política de chamada das senhas.

- FIFO (ordem de chegada)
- Prioridade

---

## Observer

Notifica automaticamente os interessados quando ocorre alguma alteração em uma senha.

Observadores implementados:

- Auditoria
- Painel
- Métricas

---

## Factory Method

Responsável pela criação dos canais de notificação utilizados pelo sistema.

---

# ✨ Funcionalidades

O sistema atualmente possui as seguintes funcionalidades:

## 🎛️ Painel do Operador

Tela principal utilizada pelos atendentes.

Permite:

- gerar senhas;
- chamar próxima senha;
- finalizar atendimento;
- cancelar atendimento;
- reativar senhas canceladas;
- acompanhar o resumo geral da fila.

---

## 📺 Visão do Cliente

Painel destinado às TVs da sala de espera.

Exibe:

- senha em atendimento;
- próximas senhas da fila;
- últimas chamadas;
- alerta sonoro automático;
- atualização em tempo real.

---

## 📊 Painel de Métricas

Painel destinado aos gestores.

Exibe indicadores como:

- tempo médio de espera;
- tempo médio de atendimento;
- atendimentos por serviço;
- esperas por prioridade;
- taxa de cancelamento.

---

## 🔄 Reativação de Senhas

Permite reativar senhas previamente canceladas.

Quando uma senha é reativada:

- retorna ao estado **AGUARDANDO**;
- volta a participar normalmente da fila;
- permanece com seus dados originais (cliente, serviço e prioridade).

---

# 🌐 API REST

| Método | Endpoint | Descrição |
|---------|----------|-----------|
| GET | /api/senhas | Lista todas as senhas |
| POST | /api/senhas | Cria nova senha |
| POST | /api/senhas/proxima | Chama próxima senha |
| POST | /api/senhas/{numero}/finalizar | Finaliza atendimento |
| POST | /api/senhas/{numero}/cancelar | Cancela senha |
| POST | /api/senhas/{numero}/reativar | Reativa senha cancelada |
| GET | /api/painel | Retorna resumo da fila |
| GET | /api/metricas | Retorna estatísticas do sistema |

---

# 📚 Documentação

A documentação arquitetural encontra-se em:

- **docs/defesa.md**
- **docs/defesa.pdf**

Diagramas:

- C4 Contexto
- C4 Container

ADRs:

- ADR 001 – Arquitetura em Camadas
- ADR 002 – Persistência em Memória
- ADR 003 – Padrões de Projeto

---

# 🚀 Execução

## Windows

```powershell
.\run.bat
```

## Linux/macOS

```bash
chmod +x run.sh
./run.sh
```

Após iniciar o servidor:

| Tela | Endereço |
|------|----------|
| Painel do Operador | http://localhost:8080 |
| Visão do Cliente | http://localhost:8080/cliente.html |
| Painel de Métricas | http://localhost:8080/metricas.html |

---

# 🧪 Testes

Windows

```powershell
.\test.bat
```

Linux/macOS

```bash
./test.sh
```