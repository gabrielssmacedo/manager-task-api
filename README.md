![Imagem do Sistema](assets/task-logo-git.png)

O Manager Task é uma API REST para gerenciar tarefas diárias, melhorando a organização, clareza no que deve ser feito e produtividade. Ela permite aos usuários criar, visualizar, atualizar e excluir tarefas, oferecendo um conjunto completo de operações para controle de atividades.

Confira a documentação no Swagger: https://macedo-manager-task.up.railway.app/swagger-ui/index.html#/

## Funcionalidades

Tarefas:
- Criar tarefa
- Buscar tarefa
- Buscar tarefa por Id
- Modificar tarefa
- Deletar tarefa
- Concluir tarefa
- Buscar tarefas da semana
- Buscar tarefas de hoje
- Buscar tarefas por status

Subtarefas:
- Criar subtarefa
- Buscar subtarefas
- Deletar subtarefa


## Tecnologias
- Java 21
- Spring Boot 3.5.3
- Maven
- Swagger
- Banco H2 (testes)
- PostgreSQL (Via Railway)
- Railway (deploy)


## Como Executar o Projeto?

Caso o link não esteja disponível, você pode rodar em sua máquina, com esse passo a passo:

### Pré-requisitos

Certifique-se de ter instalado:

- Java Development Kit (JDK) 21 ou superior
- Maven
- Git

Clone o repositório:

``` bash
git clone https://github.com/gabrielssmacedo/manager-task-api.git
cd manager-task-api
```

Compile e execute o projeto:

``` bash
mvn clean install
mvn spring-boot:run
```

Acesse a API:
- A API estará disponível em http://localhost:8080.  
- Você pode acessar a documentação interativa da API (Swagger UI) em http://localhost:8080/swagger-ui.html.

## 
Linkedin: https://www.linkedin.com/in/gabrielsantosmacedo/
