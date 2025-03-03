# Coffee Machine API

## 📌 Описание
Этот проект реализует API для управления кофемашиной, которая умеет:
- Готовить напитки (эспрессо, американо, капучино);
- Контролировать запасы ингредиентов;
- Запоминать новые рецепты;
- Вести статистику популярности напитков;
- Работать только в рабочие часы (8:00 - 17:00, без выходных и праздников).

## 🚀 Технологии
- Java 17+
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Caffeine Cache
- Swagger UI
- REST API

## 🔧 Запуск проекта

### 1️⃣ Создание базы данных PostgreSQL
Перед запуском создайте базу данных `coffeemachine` и выполните следующий SQL-запрос для добавления стандартных напитков:

```sql
CREATE TABLE ingredients (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    quantity INT NOT NULL
);

CREATE TABLE drinks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL
);

CREATE TABLE drink_ingredients (
    drink_id INT REFERENCES drinks(id),
    ingredient VARCHAR(255) NOT NULL,
    amount INT NOT NULL,
    PRIMARY KEY (drink_id, ingredient)
);

-- Добавление ингредиентов
INSERT INTO ingredients (name, quantity) VALUES ('Coffee', 1000);
INSERT INTO ingredients (name, quantity) VALUES ('Water', 5000);
INSERT INTO ingredients (name, quantity) VALUES ('Milk', 2000);

-- Добавление стандартных напитков
INSERT INTO drinks (name) VALUES ('Эспрессо');
INSERT INTO drinks (name) VALUES ('Американо');
INSERT INTO drinks (name) VALUES ('Капучино');

-- Связь напитков с ингредиентами
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (1, 'Coffee', 10);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (1, 'Water', 30);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (2, 'Coffee', 10);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (2, 'Water', 50);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (3, 'Coffee', 10);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (3, 'Milk', 40);
INSERT INTO drink_ingredients (drink_id, ingredient, amount) VALUES (3, 'Water', 20);
```

### 2️⃣ Настройка `application.properties`
Файл `src/main/resources/application.properties` должен содержать:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/coffeemachine
spring.datasource.username=your_user
spring.datasource.password=your_password
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

spring.cache.type=caffeine

springdoc.swagger-ui.path=/swagger-ui.html
```

Замените `your_user` и `your_password` на ваши данные PostgreSQL.

### 3️⃣ Запуск проекта

#### Через командную строку
```sh
mvn spring-boot:run
```

#### Через IDE (IntelliJ IDEA, VS Code и т. д.)
- Открыть проект
- Запустить класс `CoffeemachineApplication`

## 📌 API Документация
После запуска API доступно в Swagger:
[Swagger UI](http://localhost:8080/swagger-ui.html)

## 🔥 Основные эндпоинты

| Метод    | URL                                     | Описание                                |
|----------|-----------------------------------------|-----------------------------------------|
| `GET`    | `/api/machines/drinks`                 | Получить список всех напитков          |
| `POST`   | `/api/machines/brew?drinkName=Эспрессо` | Приготовить напиток                    |
| `POST`   | `/api/machines/add-recipe`             | Добавить новый рецепт                   |
| `GET`    | `/api/machines/ingredients`            | Получить список ингредиентов           |
| `PUT`    | `/api/machines/ingredient/{id}?quantity=500` | Обновить количество ингредиента |
| `DELETE` | `/api/machines/drink/{id}`             | Удалить напиток                        |
| `DELETE` | `/api/machines/ingredient/{id}`        | Удалить ингредиент                     |
| `GET`    | `/api/machines/stats`                  | Получить самый популярный напиток      |



