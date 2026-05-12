# JavaFX Chat

Многопользовательский чат с комнатами, историей сообщений и обменом файлами.

## Технологии

- Java 17
- JavaFX 21
- Maven
- WebSocket 
- Gson
- Hibernate + PostgreSQL
- BCrypt
- Docker

## Структура проекта

- `chat-api` — общие модели
- `chat-server` — WebSocket сервер
- `chat-client` — JavaFX клиент

## Запуск

### 1. Клонировать репозиторий

```bash
git clone https://github.com/OOP24211/lugovskoi_java_labs.git
cd lugovskoi_java_labs
git checkout chat
```

### 2. Создать .env файл

Скопируй `.env.example` и заполни:

```bash
cp .env.example .env
```

Открой `.env` и заполни значения:

```
DB_HOST=localhost
DB_PORT=5432
DB_NAME=chat_db
DB_USER=postgres
DB_PASSWORD=придумать пароль
SERVER_PORT=8080
SERVER_URL=ws://localhost:8080
```

### 3. Запустить базу данных

Установи Docker если не установлен: https://www.docker.com/products/docker-desktop

Затем в корне проекта:

```bash
docker-compose up -d
```

Postgres поднимется автоматически с параметрами из `.env`.

### 4. Запустить сервер

```bash
cd chat-server
mvn exec:java
```

### 5. Запустить клиент

```bash
cd chat-client
mvn javafx:run
```

## Требования

- Java 17+
- Maven
- Docker
