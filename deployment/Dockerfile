# ETAPA 1: Builder - Extrae las capas del JAR
FROM eclipse-temurin:21-jdk-alpine as builder
WORKDIR /app
COPY applications/app-service/build/libs/Auth.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

# ETAPA 2: Imagen Final - Construye la imagen de producción
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Crea usuario y grupo no-root por seguridad
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# Crea y da permisos al directorio de caché para AppCDS
RUN mkdir /cache && \
    chown -R appuser:appgroup /app && \
    chown -R appuser:appgroup /cache

# Cambia al usuario no-root
USER appuser

# Copia las capas en orden de la menos a la más volátil
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/application/ ./

# El ENTRYPOINT que ejecutará ECS
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70", "org.springframework.boot.loader.launch.JarLauncher"]