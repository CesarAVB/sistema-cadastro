# Sistema de Cadastro de Clientes - Frontend

Sistema web para gerenciamento de clientes com autenticação JWT, desenvolvido em Angular 19.

## 🚀 Tecnologias

- Angular 19
- TypeScript
- RxJS
- Angular Material / PrimeNG
- Bootstrap 5

## 📋 Pré-requisitos

- Node.js 18+ 
- npm ou yarn
- Angular CLI 19
```bash
npm install -g @angular/cli@19
```

## 🔧 Instalação

1. Clone o repositório
```bash
git clone <url-do-repositorio>
cd sistema-cadastro-frontend
```

2. Instale as dependências
```bash
npm install
```

3. Configure o ambiente
```bash
# Edite src/environments/environment.ts
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

4. Execute o projeto
```bash
ng serve
```

Acesse: `http://localhost:4200`

## 🏗️ Estrutura do Projeto
```
src/
├── app/
│   ├── core/               # Serviços e guards centrais
│   ├── modules/            # Módulos de funcionalidades
│   │   ├── auth/          # Autenticação
│   │   ├── cliente/       # Gestão de clientes
│   │   └── shared/        # Componentes compartilhados
│   ├── app.component.ts
│   └── app-routing.module.ts
├── assets/                 # Recursos estáticos
└── environments/           # Configurações de ambiente
```

## 🔐 Autenticação

O sistema utiliza JWT (JSON Web Token) para autenticação:

1. Login em `/auth/login`
2. Token armazenado no localStorage
3. Interceptor adiciona token automaticamente nas requisições
4. Guard protege rotas que exigem autenticação

### Credenciais Padrão
```
Username: gutobrrj
Password: 26021988
```

## 📦 Funcionalidades

### Autenticação
- [x] Login com JWT
- [x] Logout
- [x] Proteção de rotas
- [x] Interceptor HTTP

### Clientes
- [x] Listar clientes
- [x] Cadastrar cliente
- [ ] Editar cliente
- [ ] Excluir cliente
- [ ] Buscar cliente

### Cadastro de Cliente
- Dados pessoais (nome, CPF/CNPJ, RG, etc)
- Múltiplos endereços com busca de CEP
- Múltiplos contatos (email e telefone)
- Credenciais de acesso

## 🛠️ Scripts Disponíveis
```bash
# Desenvolvimento
ng serve

# Build produção
ng build --configuration production

# Testes unitários
ng test

# Testes e2e
ng e2e

# Linting
ng lint
```

## 🌐 Integração com Backend

Configure a URL da API em `src/environments/environment.ts`:
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080'
};
```

### Endpoints Utilizados

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/auth/login` | Autenticação |
| POST | `/cliente` | Criar cliente |
| GET | `/cliente` | Listar clientes |

## 📝 Modelos de Dados

### Cliente
```typescript
interface Cliente {
  id?: number;
  nome: string;
  dataNascimento: Date;
  tipoPessoa: 'FISICA' | 'JURIDICA';
  cpfCnpj: string;
  rg: string;
  genero: 'FEMININO' | 'MASCULINO';
  enderecos: Endereco[];
  contatos: Contato[];
  login?: string;
  senha?: string;
}
```

### Endereco
```typescript
interface Endereco {
  id?: number;
  cep: string;
  logradouro: string;
  numero: string;
  complemento?: string;
  bairro: string;
  cidade: string;
  uf: string;
}
```

### Contato
```typescript
interface Contato {
  id?: number;
  tipo: 'EMAIL' | 'TELEFONE';
  valor: string;
}
```

## 🎨 Personalização

### Temas
Edite `src/styles.css` para personalizar cores e estilos globais.

### Validações
Customize validações em `src/app/modules/cliente/cadastro-cliente/cadastro-cliente.component.ts`

## 🐛 Resolução de Problemas

### CORS
Se encontrar problemas de CORS, verifique a configuração no backend em `SecurityConfig.java`

### Token Expirado
Tokens expiram após 1 hora. O sistema redireciona automaticamente para login.

## 📄 Licença

Este projeto está sob licença privada.

## 👥 Autor

César Augusto - Desenvolvedor Java/Angular

## 🤝 Contribuindo

1. Fork o projeto
2. Crie uma branch para sua feature (`git checkout -b feature/NovaFuncionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/NovaFuncionalidade`)
5. Abra um Pull Request
