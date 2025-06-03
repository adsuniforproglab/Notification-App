# Aplicativo de Notificação

Um microsserviço Spring Boot para envio de notificações SMS via Amazon SNS baseado em eventos de fila de mensagens RabbitMQ.

## Visão Geral

O Aplicativo de Notificação é uma aplicação Java Spring Boot que consome eventos relacionados a propostas de filas RabbitMQ e envia notificações SMS para usuários via AWS SNS. Foi projetado para lidar com dois tipos principais de notificação:
- Notificações de propostas pendentes
- Notificações de propostas concluídas

## Funcionalidades

- Consumo de mensagens de filas RabbitMQ
- Entrega de notificações SMS via Amazon SNS
- Tratamento de erros e mecanismos de fallback
- Mensagens de notificação personalizáveis
- Log abrangente
- Deploy containerizado
- Monitoramento de saúde via Spring Actuator

## Stack Tecnológica

- Java 21
- Spring Boot 3.4.5
- Spring AMQP (RabbitMQ)
- AWS SDK para SNS
- Docker & Docker Compose
- Lombok
- JUnit 5 & Mockito para testes

## Pré-requisitos

- Java 21 ou superior
- Maven
- Servidor RabbitMQ (ou Docker)
- Conta AWS com permissões SNS
- Docker & Docker Compose (para deploy containerizado)

## Configuração

A aplicação pode ser configurada usando variáveis de ambiente ou um arquivo `.env`. Os principais parâmetros de configuração incluem:

### Configuração RabbitMQ
```properties
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
```

### Configuração AWS
```properties
AWS_ACCESS_KEY=sua_chave_de_acesso_aws
AWS_SECRET_KEY=sua_chave_secreta_aws
AWS_REGION=us-east-1
```

### Configuração de Notificação
```properties
DEFAULT_PHONE=seu_numero_telefone_padrao_fallback
```

## Começando

### Executando Localmente

1. Clone o repositório
2. Crie um arquivo `.env` com sua configuração (veja o modelo na seção Configuração)
3. Execute a aplicação usando Maven:
```bash
./mvnw spring-boot:run
```

### Usando Docker

1. Construa a imagem Docker:
```bash
docker build -t notification-app .
```

2. Execute usando Docker Compose:
```bash
docker-compose up -d
```

## Formatos de Mensagem

A aplicação espera mensagens de proposta com a seguinte estrutura:

```json
{
  "id": 123,
  "proposalValue": 5000.0,
  "approved": true,
  "integrated": true,
  "observation": "Mensagem personalizada opcional",
  "user": {
    "id": 456,
    "name": "João",
    "lastName": "Silva",
    "tellPhone": "+5511987654321"
  }
}
```

## Testes

Execute os testes usando Maven:

```bash
./mvnw test
```

Ou use a tarefa de teste do VS Code:

```bash
# Executar todos os testes
mvn -B test
```

## Verificações de Saúde

A aplicação expõe endpoints de saúde através do Spring Actuator:

- Saúde: `/actuator/health`
- Informações: `/actuator/info`
- Métricas: `/actuator/metrics`

## Monitoramento

O Spring Actuator fornece métricas detalhadas para monitoramento. A configuração Docker inclui verificações de saúde para garantir que a aplicação esteja executando corretamente.

---
