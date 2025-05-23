# Aplicativo de Notificação

Um microsserviço Spring Boot para envio de notificações SMS via Amazon SNS baseado em eventos de fila de mensagens RabbitMQ.

## Visão Geral

O Aplicativo de Notificação é uma aplicação Java Spring Boot que consome eventos relacionados a propostas de filas RabbitMQ e envia notificações SMS para usuários via AWS SNS. Ele é projetado para lidar com dois tipos principais de notificação:
- Notificações de propostas pendentes
- Notificações de propostas concluídas

## Funcionalidades

- Consumo de mensagens de filas RabbitMQ
- Entrega de notificações SMS via Amazon SNS
- Tratamento de erros e mecanismos de fallback
- Mensagens de notificação personalizáveis
- Log abrangente
- Implantação containerizada
- Monitoramento de saúde via Spring Actuator

## Pilha de Tecnologia

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
- Docker & Docker Compose (para implantação containerizada)

## Configuração

A aplicação pode ser configurada usando variáveis de ambiente ou um arquivo `.env`. Os principais parâmetros de configuração incluem:

### Configuração RabbitMQ
```properties
RABBITMQ_HOST=localhost
RABBITMQ_PORT=5672
RABBITMQ_USERNAME=guest
RABBITMQ_PASSWORD=guest
Configuração AWS
Properties

AWS_ACCESS_KEY=sua_aws_access_key
AWS_SECRET_KEY=sua_aws_secret_key
AWS_REGION=us-east-1
Configuração de Notificação
Properties

DEFAULT_PHONE=seu_numero_de_telefone_de_fallback_padrao
Começando
Executando Localmente
Clone o repositório
Crie um arquivo .env com sua configuração (veja o template na seção Configuração)
Execute a aplicação usando Maven:
Bash

./mvnw spring-boot:run
Usando Docker
Construa a imagem Docker:
Bash

docker build -t notification-app .
Execute usando Docker Compose:
Bash

docker-compose up -d
Formatos de Mensagem
A aplicação espera mensagens de proposta com a seguinte estrutura:

JSON

{
  "id": 123,
  "proposalValue": 5000.0,
  "approved": true,
  "integrated": true,
  "observation": "Optional custom message",
  "user": {
    "id": 456,
    "name": "John",
    "lastName": "Doe",
    "tellPhone": "+1234567890"
  }
}
Testes
Execute os testes usando Maven:

Bash

./mvnw test
Ou use a tarefa de teste do VS Code:

Bash

# Executar todos os testes
mvn -B test
Verificações de Saúde
A aplicação expõe endpoints de saúde através do Spring Actuator:

Saúde: /actuator/health
Informações: /actuator/info
Métricas: /actuator/metrics
Monitoramento
O Spring Actuator fornece métricas detalhadas para monitoramento. A configuração do Docker inclui verificações de saúde para garantir que a aplicação esteja funcionando corretamente.

Contribuidor
Leonardo Meireles
