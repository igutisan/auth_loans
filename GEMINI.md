# Contexto del Proyecto: Auth

## 1. Descripción General

Este proyecto es una aplicación llamada "Auth" construida con Java y Spring Boot. Sigue los principios de la **Arquitectura Limpia (Clean Architecture)** para separar las preocupaciones y mantener un bajo acoplamiento entre los componentes. El proyecto está configurado como un proyecto multi-módulo de Gradle.

## 2. Arquitectura

El proyecto está estructurado en las siguientes capas, desde la más interna hasta la más externa:

### Capa de Dominio (Core)
- **`domain/model`**: Contiene las entidades y objetos de valor del negocio. Representa el corazón de la aplicación.
- **`domain/usecase`**: Implementa los casos de uso específicos de la aplicación, orquestando la lógica de negocio.

### Capa de Infraestructura
- **`infrastructure/driven-adapters`**: Implementaciones concretas para interactuar con herramientas externas como bases de datos, servicios REST, etc.
  - **`r2dbc-postgresql`**: Adaptador para la comunicación reactiva con una base de datos PostgreSQL.
  - **`logger-adapter`**: Adaptador para la gestión de logs.
- **`infrastructure/entry-points`**: Puntos de entrada a la aplicación.
  - **`reactive-web`**: Expone APIs REST reactivas.
- **`infrastructure/helpers`**: Clases de utilidad reusables para la capa de infraestructura.

### Capa de Aplicación
- **`applications/app-service`**: Es el módulo principal que ensambla toda la aplicación. Es responsable de la inyección de dependencias, la configuración y el arranque del servicio (contiene el método `main`).

## 3. Stack Tecnológico

- **Lenguaje**: Java 21
- **Framework**: Spring Boot 3.5.4
- **Programación Reactiva**: Project Reactor
- **Base de Datos**: PostgreSQL (gestionado con Docker Compose para desarrollo local)
- **Acceso a Datos**: R2DBC (Reactivo)
- **Build Tool**: Gradle 8.14.3
- **Utilidades**:
  - Lombok
  - MapStruct
- **Testing**:
  - JUnit 5
  - Jacoco (Cobertura de código)
  - Pitest (Mutation Testing)
- **Calidad de Código**: SonarQube

## 4. Módulos del Proyecto

El `settings.gradle` define la siguiente estructura de módulos:

- `rootProject.name = 'Auth'`
- `:app-service` -> `applications/app-service`
- `:model` -> `domain/model`
- `:usecase` -> `domain/usecase`
- `:r2dbc-postgresql` -> `infrastructure/driven-adapters/r2dbc-postgresql`
- `:reactive-web` -> `infrastructure/entry-points/reactive-web`
- `:logger-adapter` -> `infrastructure/driven-adapters/logger-adapter`

## 5. Entorno de Desarrollo Local

Para levantar el entorno de desarrollo local, se utiliza Docker.

- **Comando**: `docker-compose up -d`
- **Servicio**: PostgreSQL 16
- **Contenedor**: `postgres-test`
- **Credenciales**:
  - **Usuario**: `test`
  - **Contraseña**: `P@55w0rd`
  - **Base de Datos**: `test`
- **Puerto**: `5432`

## 6. Comandos de Build y Testing

- **Build**: El proyecto se construye usando el wrapper de Gradle: `./gradlew build`
- **Tests**: Los tests se ejecutan como parte del proceso de build. También se pueden ejecutar explícitamente con `./gradlew test`.
- **Reportes**:
  - **Jacoco**: Genera reportes de cobertura en `build/reports/jacoco/`.
  - **Pitest**: Genera reportes de mutation testing en `build/reports/pitest/`.
