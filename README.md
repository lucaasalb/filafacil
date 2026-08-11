# 🏥 FilaFácil — Sistema de Gestão de Filas de Atendimento

Bem-vindo ao repositório do **FilaFácil**, projeto final da disciplina de **Arquitetura de Software** (UFMA).

O **FilaFácil** é um sistema completo para organização e gestão de filas de atendimento (bancos, clínicas, cartórios, etc.). O projeto permite a geração de senhas, chamadas baseadas em diferentes políticas (ordem de chegada ou prioridade), acompanhamento dos status das senhas e exibição em tempo real na **Visão do Cliente (Painel de TV da Sala de Espera)**.

> ⚡ **Destaque de Engenharia:** Desenvolvido em **Java 21 sem nenhuma biblioteca ou framework externo**. Toda a arquitetura web (servidor HTTP, rotas REST e renderização estática), persistência e testes automatizados foram construídos utilizando recursos nativos da linguagem Java para manter as decisões de arquitetura e padrões de projeto 100% visíveis e desacoplados.

---

## 📁 Estrutura do Repositório

O projeto principal está organizado dentro da pasta [`filafacil-estudante/`](filafacil-estudante):

```text
filafacil/
├── filafacil-estudante/        # Diretório do projeto da aplicação
│   ├── docs/                   # Documentação arquitetural e de defesa
│   │   ├── adr/                # Registros de Decisões de Arquitetura (ADRs)
│   │   ├── architecture/       # Diagramas C4 (Contexto e Container)
│   │   └── defesa.md           # Documento completo de defesa do trabalho
│   ├── src/                    # Código-fonte (Java 21 e Frontend HTML/CSS/JS)
│   │   ├── main/java/          # Camadas (model, service, repository, patterns, web)
│   │   ├── main/resources/     # Interface gráfica da aplicação web
│   │   └── test/java/          # Suíte de testes automatizados sem frameworks
│   ├── run.bat                 # Script de execução (Windows)
│   ├── run.sh                  # Script de execução (Linux/macOS)
│   ├── test.bat                # Script de testes (Windows)
│   └── test.sh                 # Script de testes (Linux/macOS)
└── README.md                   # Este arquivo (Visão Geral do Repositório)
```

---

## 🏛️ Arquitetura e Padrões de Projeto

O sistema foi estruturado seguindo uma **Arquitetura em Camadas (Layered Architecture)** com **baixo acoplamento** e **alta coesão**:

* **Camada Web (`web`):** Servidor HTTP nativo e tradução de requisições JSON.
* **Camada Service (`service`):** Casos de uso e orquestração das regras de negócio.
* **Camada Domain (`model`):** Entidades e regras de domínio (`Senha`, `Prioridade`, etc.).
* **Camada Repository (`repository`):** Abstração de armazenamento (memória).

### Padrões de Projeto Aplicados:
1. **Strategy (`patterns/strategy`):** Alterna dinamicamente a regra de chamada de fila (FIFO vs. Prioridade Primeiro).
2. **Observer (`patterns/observer`):** Notifica observadores (`ObservadorAuditoria` e `ObservadorPainel`) a cada mudança de estado da senha.
3. **Factory Method (`patterns/factory`):** Abstrai a criação de canais de notificação ao cliente (Console, E-mail, etc.).

---

## 📚 Documentações do Projeto

Para consultar os detalhes arquiteturais, diagramas e justificativas das decisões de projeto, acesse os documentos abaixo:

* 📄 **[Documento Completo de Defesa](filafacil-estudante/docs/defesa.md):** Apresentação detalhada da arquitetura, funcionalidades, telas e decisões.
* 📐 **[Diagrama C4 - Nível 1 (Contexto)](filafacil-estudante/docs/architecture/c4-contexto.md):** Visão geral de contexto do sistema e seus atores.
* 📦 **[Diagrama C4 - Nível 2 (Container)](filafacil-estudante/docs/architecture/c4-container.md):** Visão dos containers e comunicação HTTP.
* 📜 **[ADR 001 - Arquitetura em Camadas](filafacil-estudante/docs/adr/001-arquitetura-em-camadas.md)**
* 📜 **[ADR 002 - Persistência em Memória](filafacil-estudante/docs/adr/002-persistencia-em-memoria.md)**
* 📜 **[ADR 003 - Padrões de Projeto (GoF)](filafacil-estudante/docs/adr/003-padroes-de-projeto.md)**

---

## 🚀 Como Executar o Projeto

### Pré-requisito
* **Java 21 (JDK 21)** ou superior instalado.

### Executando no Windows
Abra o terminal na pasta [`filafacil-estudante/`](filafacil-estudante) e execute:
```cmd
run.bat
```
*(Se estiver usando PowerShell, execute `.\run.bat`)*

### Executando no Linux ou macOS
```bash
cd filafacil-estudante
chmod +x run.sh test.sh
./run.sh
```

Após iniciar, acesse no seu navegador:
* 🎛️ **Painel do Operador:** [http://localhost:8080](http://localhost:8080)
* 📺 **Visão do Cliente (TV da Sala de Espera):** [http://localhost:8080/cliente.html](http://localhost:8080/cliente.html)

---

## 🧪 Rodando os Testes Automatizados

Para executar os testes sem bibliotecas externas:

* **Windows:** `.\test.bat` (dentro da pasta `filafacil-estudante/`)
* **Linux/macOS:** `./test.sh` (dentro da pasta `filafacil-estudante/`)
