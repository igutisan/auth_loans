---
apply: always
---

# Agents

This document defines the **rules**, **responsibilities**, and **capabilities** of the agent that assists in Spring Boot development following Clean Architecture, SOLID principles, modern design patterns, and best practices.

---

## 1. Agent Purpose

- Generate, review, and suggest code that follows:
    - **SOLID principles**.
    - Modern and widely adopted **design patterns** (Factory, Adapter, Strategy, etc.).
    - Modular, maintainable, and testable design.
    - Clean Architecture structure (based on Bancolombia's scaffold).

- Assist with project generation using the Gradle plugin `scaffold-clean-architecture`.

---

## 2. Recommended Context (Bancolombia Clean Architecture)

This architecture divides the project into modules:

- **entry-points**: entry points (REST controllers, Kafka consumers).
- **driven-adapters**: external adapters (databases, services, files, etc.).
- **helpers**: shared utilities across adapters and entry points.
- **application**: the main module that bootstraps the app, Spring configuration, and dependency injection.

Projects can be generated as **imperative** (Spring Boot MVC) or **reactive** (WebFlux/Reactor), depending on the business needs.

Scaffolding supports configurable options such as: Java version (17 or 21), Lombok, Micrometer metrics, mutation testing, etc.

---

## 3. Principles and Patterns

### SOLID

- **S**ingle Responsibility: each class/module has only one reason to change.
- **O**pen/Closed: open for extension, closed for modification.
- **L**iskov Substitution: inheritance must respect contracts.
- **I**nterface Segregation: small, focused interfaces.
- **D**ependency Inversion: depend on abstractions, not concrete implementations.

### Modular Design & Boundaries

- Clear separation of concerns:
    - **Core / business logic** (entities, use cases) with no external dependencies.
    - **Adapters / details** (databases, frameworks, third-party APIs).
    - **App / configuration** (Spring Boot, controllers, DI).

### Testing & Independence

- Business logic must be fully testable without infrastructure.
- Infrastructure elements (DB, APIs) should use integration tests or Testcontainers, never leak into the core.

---

## 4. Agent Behavior

### Project Initialization

- Suggest Gradle command for project generation:
  ```bash
  gradle ca --name=MyProject --type=imperative --lombok=true --metrics=true --mutation=true --javaVersion=VERSION_21
