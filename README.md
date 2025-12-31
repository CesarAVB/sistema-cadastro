# Sistema de Gestão de Clientes

Sistema completo de gerenciamento de clientes desenvolvido com **Spring Boot 3** no backend e **Angular 19** no frontend, incluindo autenticação JWT, documentação Swagger e arquitetura RESTful.

## 📋 Sobre o Projeto

API REST para gerenciamento completo de clientes, incluindo cadastro de dados pessoais, endereços e contatos. O sistema oferece recursos de dashboard com métricas em tempo real, autenticação segura e controle de permissões baseado em roles.

## 🚀 Tecnologias Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Security** - Autenticação e autorização
- **Spring Data JPA** - Persistência de dados
- **MySQL** - Banco de dados
- **Flyway** - Versionamento do banco de dados
- **JWT (java-jwt 4.4.0)** - Tokens de autenticação
- **ModelMapper 3.0.0** - Conversão entre DTOs e entidades
- **Swagger/OpenAPI (SpringDoc 2.3.0)** - Documentação da API
- **BCrypt** - Criptografia de senhas

### Arquitetura
- Padrão MVC (Model-View-Controller)
- DTOs para transferência de dados
- Repository pattern
- Service layer
- Exception handling centralizado

## 📁 Estrutura do Projeto

```
src/main/java/br/com/sistema/
├── config/              # Configurações (CORS, Swagger, ModelMapper)
├── controller/          # Controladores REST
├── dto/                 # Data Transfer Objects
├── exceptions/          # Tratamento de exceções
├── model/               # Entidades JPA
│   └── enums/          # Enumerações
├── repository/          # Interfaces JPA Repository
├── security/            # Configuração de segurança
│   ├── controller/     # Controller de autenticação
│   ├── dto/            # DTOs de segurança
│   └── service/        # Serviços de segurança
├── service/             # Lógica de negócio
└── util/                # Classes utilitárias

src/main/resources/
├── db/migration/        # Scripts Flyway
└── application.properties
```

## ⚙️ Configuração e Instalação

### Pré-requisitos
- Java 17+
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse ou VS Code)

### Instalação Local

1. **Clone o repositório**
```bash
git clone <url-do-repositorio>
cd sistema-cadastro
```

2. **Configure o banco de dados**

Crie um arquivo `application-local.properties` em `src/main/resources/`:

```properties
# Datasource
spring.datasource.url=jdbc:mysql://localhost:3306/syscadastro?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha

# JWT Secret
security.api.token.secret=sua-chave-secreta-aqui
```

3. **Execute as migrations**
```bash
mvn flyway:migrate
```

4. **Compile e execute o projeto**
```bash
mvn clean install
mvn spring-boot:run
```

O servidor estará disponível em `http://localhost:8080`

## 🔐 Autenticação

### Credenciais Padrão
- **Username:** `admin`
- **Password:** `admin123`
- **Email:** `admin@admin.com`

### Endpoint de Login
```
POST /auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

### Resposta
```json
{
  "username": "admin",
  "authenticated": true,
  "created": "2025-01-01T10:00:00",
  "expiration": "2025-01-01T11:00:00",
  "accessToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
}
```

### Usando o Token
Adicione o token no header de todas as requisições protegidas:
```
Authorization: Bearer {seu-token-aqui}
```

## 📚 Documentação da API

A documentação interativa está disponível via Swagger UI:

- **Desenvolvimento:** `http://localhost:8080/swagger-ui.html`
- **Documentação JSON:** `http://localhost:8080/api-docs`

## 🔌 Principais Endpoints

### Clientes
- `POST /cliente` - Criar novo cliente
- `GET /cliente` - Listar todos os clientes
- `GET /cliente/{id}` - Buscar cliente por ID
- `PUT /cliente/{id}` - Atualizar cliente
- `DELETE /cliente/{id}` - Deletar cliente
- `GET /cliente/metricas` - Obter métricas do dashboard
- `GET /cliente/recentes?limite=4` - Obter clientes recentes

### Autenticação
- `POST /auth/login` - Realizar login

## 🗄️ Modelo de Dados

### Cliente
- Dados pessoais (nome, CPF/CNPJ, RG, gênero)
- Data de nascimento
- Tipo de pessoa (Física/Jurídica)
- Login e senha
- Lista de endereços
- Lista de contatos
- Data de cadastro

### Endereço
- CEP, logradouro, número
- Complemento, bairro
- Cidade, UF

### Contato
- Tipo (EMAIL/TELEFONE)
- Valor

## 🛡️ Segurança

- **Autenticação JWT** com expiração de 1 hora
- **BCrypt** para hash de senhas
- **CORS** configurado para origem específica
- **Sessões stateless**
- **Controle de permissões** baseado em roles (ADMIN, MANAGER, COMMON_USER)

## 🚀 Deploy em Produção

O projeto está configurado para deploy via Railway:

1. Configure as variáveis de ambiente:
```
MYSQLHOST=seu-host
MYSQLPORT=3306
MYSQLDATABASE=syscadastro
MYSQLUSER=seu-usuario
MYSQLPASSWORD=sua-senha
JWT_SECRET=sua-chave-jwt-secreta
PORT=8080
SPRING_PROFILES_ACTIVE=prod
```

2. O Swagger é automaticamente desabilitado em produção

## 🧪 Testando a API

### Exemplo de cadastro de cliente
```json
POST /cliente
Authorization: Bearer {token}
Content-Type: application/json

{
  "nome": "João Silva",
  "dataNascimento": "1990-05-15",
  "tipoPessoa": "FISICA",
  "cpfCnpj": "12345678901",
  "rg": "1234567",
  "genero": "MASCULINO",
  "enderecos": [
    {
      "cep": "12345-678",
      "logradouro": "Rua Exemplo",
      "numero": "100",
      "bairro": "Centro",
      "cidade": "São Paulo",
      "uf": "SP"
    }
  ],
  "contatos": [
    {
      "tipo": "EMAIL",
      "valor": "joao@email.com"
    },
    {
      "tipo": "TELEFONE",
      "valor": "(11) 98765-4321"
    }
  ],
  "login": "joao.silva",
  "senha": "senha123"
}
```

## 📝 Migrations Flyway

O projeto utiliza Flyway para versionamento do banco de dados. As migrations estão em `src/main/resources/db/migration/`:

- V1: Tabela Cliente
- V2: Tabela Endereco
- V3: Tabela Contato
- V4: Tabela Permission
- V5: Tabela Usuario
- V6: Insert de Permissions
- V7: Tabela User_Permission
- V8: Insert de Usuario admin
- V9: Insert de User_Permission para admin

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFeature`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/NovaFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT - veja o arquivo LICENSE para detalhes.

## 👨‍💻 Desenvolvedor

**César Augusto**
- Email: cesar.augusto.rj1@gmail.com
- Portfolio: https://quemsoueu-six.vercel.app/

## 🔗 URLs do Projeto

- **Frontend (Produção):** https://syscadastro.cesaravb.com.br
- **Frontend (Local):** http://localhost:4200
- **Backend (Local):** http://localhost:8080
- **Swagger (Local):** http://localhost:8080/swagger-ui.html
