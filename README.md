# Microblogging Platform

Una versión simplificada de una plataforma de microblogging similar a Twitter, que permite a los usuarios publicar mensajes cortos (tweets), seguir a otros usuarios y ver un timeline de tweets de los usuarios a los que siguen.

## Requisitos

- JDK 17
- Maven
- Docker (opcional, para ejecutar la aplicación en un contenedor)

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit 5
- Mockito

## Configuración del Proyecto

### Clonar el Repositorio


git clone https://github.com/theallison1/ualachallenge1.git

cd ualachallenge1


## Compilar y Ejecutar la Aplicación
  mvn clean package
  
  mvn spring-boot:run
  
 Esto compilará el proyecto y ejecutará la aplicación Spring Boot utilizando la configuración local.

## Ejecutar con Docker (Opcional)
 Si prefieres ejecutar la aplicación utilizando Docker, asegúrate de tener Docker instalado en tu sistema y luego utiliza el siguiente Dockerfile:

## Dockerfile

### Utiliza una imagen de OpenJDK 17 como base
FROM openjdk:17-jdk-alpine

### Copia el JAR construido de tu aplicación al contenedor
COPY target/uala-0.0.1-SNAPSHOT.jar /app/uala.jar

### Expone el puerto 8080 en el contenedor
EXPOSE 8080

### Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "/app/uala.jar"]

### Para construir la imagen Docker y ejecutar el contenedor:
### Construir la imagen Docker
docker build -t microblogging-app .

### Ejecutar el contenedor Docker
docker run -p 8080:8080 microblogging-app

### La aplicación estará disponible en http://localhost:8080.




## Despliegue en Heroku
La aplicación ya está desplegada en Heroku. Puedes acceder a ella utilizando la siguiente URL:

## Microblogging Platform en Heroku

https://ualachallenge-4d208bc380f1.herokuapp.com/

## Documentación API (Swagger)
Para explorar la documentación de la API en Swagger:

## Swagger UI en Heroku
https://ualachallenge-4d208bc380f1.herokuapp.com/swagger-ui/index.html#/

## Notas Adicionales


Este README ahora incluye un enlace directo a la documentación de Swagger en tu aplicación desplegada en Heroku. 

