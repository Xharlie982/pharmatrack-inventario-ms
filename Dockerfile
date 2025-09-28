# Empaqueta la app en un contenedor JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/inventario-1.0.0.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","/app/app.jar"]
