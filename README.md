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

```bash
git clone https://github.com/theallison1/ualachallenge1.git
cd ualachallenge1


## Compilar y Ejecutar la Aplicación
  mvn clean package
  mvn spring-boot:run
Esto compilará el proyecto y ejecutará la aplicación Spring Boot utilizando la configuración local.

Despliegue en Heroku
La aplicación ya está desplegada en Heroku. Puedes acceder a ella utilizando la siguiente URL:

Microblogging Platform en Heroku

*https://ualachallenge-4d208bc380f1.herokuapp.com/
Documentación API (Swagger)
Para explorar la documentación de la API en Swagger:

Swagger UI en Heroku
https://ualachallenge-4d208bc380f1.herokuapp.com/swagger-ui/index.html#/
Notas Adicionales
Asegúrate de que tu aplicación esté correctamente configurada para manejar la base de datos en el entorno de producción de Heroku, ya que H2 Database es ideal para desarrollo pero no es adecuado para producción a largo plazo.
Considera la posibilidad de añadir variables de entorno adicionales en Heroku para configuraciones sensibles como credenciales de bases de datos o claves de API.


Este README ahora incluye un enlace directo a la documentación de Swagger en tu aplicación desplegada en Heroku. Asegúrate de ajustar cualquier otro detalle específico según las necesidades de tu proyecto antes de publicarlo.

