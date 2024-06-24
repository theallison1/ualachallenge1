# Utiliza una imagen de OpenJDK 17 como base
FROM openjdk:17-jdk-alpine

# Copia el JAR construido de tu aplicación al contenedor
COPY target/uala-0.0.1-SNAPSHOT.jar /app/uala.jar

# Expone el puerto 8080 en el contenedor
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
CMD ["java", "-jar", "/app/uala.jar"]
