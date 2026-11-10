# GameShelf

Biblioteca pessoal de jogos inspirada no conceito de diário/catalogação do Letterboxd. Projeto acadêmico Angular + Spring Boot, com CRUD persistente e autenticação JWT.

## Stack
- Frontend: Angular 20, TypeScript, standalone components, Angular Router, Reactive Forms, SCSS.
- Backend: Java 21, Spring Boot 3.5.6, Spring Security, Spring Data JPA, Jakarta Validation.
- Banco: H2 em modo arquivo (`backend/data/gameshelf`). Os dados permanecem após reiniciar a API.
- JWT: JJWT 0.12.6; senhas com BCrypt.

## Requisitos
- JDK 21 (verifique `java -version` e `javac -version`).
- Node.js 20.19+ ou 22.12+ e npm.
- Maven 3.9+ (ou use o Maven Wrapper, se adicionar um).
- VS Code recomendado.

## 1. Backend
Abra um terminal na pasta `backend`:

```powershell
cd backend
mvn spring-boot:run
```

API: `http://localhost:8080`  
H2 Console (somente desenvolvimento): `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:file:./data/gameshelf` | User: `sa` | Password: em branco.

Se `mvn` não for reconhecido, instale Maven: https://maven.apache.org/download.cgi e configure `MAVEN_HOME`/`PATH`, ou use a extensão Spring Boot no VS Code.

## 2. Frontend
Em outro terminal, na raiz do projeto:

```powershell
cd frontend
npm install
npm start
```

Abra `http://localhost:4200`.

## 3. Primeiro acesso
1. Abra `http://localhost:4200/registro`.
2. Crie sua conta com nome, e-mail e senha (mínimo 8 caracteres).
3. Entre com as credenciais. A API retorna JWT e o frontend passa a enviar `Authorization: Bearer <token>` automaticamente.
4. Cadastre jogos, avalie, escreva resenhas, altere status e exclua registros.

## Páginas
- `/login` – autenticação real na API.
- `/registro` – criação de usuário.
- `/dashboard` – indicadores da biblioteca e jogos recentes.
- `/biblioteca` – listagem, busca e filtro por status.
- `/jogos/novo` – cadastro.
- `/jogos/:id` – detalhes.
- `/jogos/:id/editar` – edição.

## API principal
| Método | Rota | Descrição |
|---|---|---|
| POST | `/api/auth/registro` | Criar conta |
| POST | `/api/auth/login` | Autenticar e receber JWT |
| GET | `/api/jogos` | Listar jogos do usuário autenticado |
| POST | `/api/jogos` | Criar jogo |
| GET | `/api/jogos/{id}` | Consultar jogo do usuário |
| PUT | `/api/jogos/{id}` | Atualizar jogo do usuário |
| DELETE | `/api/jogos/{id}` | Excluir jogo do usuário |
| GET | `/api/dashboard` | Estatísticas do usuário |

## Segurança e observações
- Todas as rotas `/api/jogos/**` e `/api/dashboard` exigem JWT.
- O acesso aos jogos é sempre limitado ao usuário autenticado.
- A senha nunca é incluída em DTOs de resposta; no banco é armazenada com BCrypt.
- O H2 está em modo arquivo, não em memória. Não apague `backend/data` se quiser manter os dados.
- CORS está liberado apenas para `http://localhost:4200` no perfil local.
- A chave JWT padrão é apenas para desenvolvimento acadêmico. Troque `app.jwt.secret` por uma chave forte via variável de ambiente antes de publicar.
- Este ZIP contém o código-fonte. As dependências são baixadas na primeira execução (`mvn` e `npm install`), portanto é necessário internet nessa etapa.

## Estrutura
```text
GameShelf/
├── backend/     # API REST Spring Boot
├── frontend/    # Aplicação Angular
└── README.md
```
