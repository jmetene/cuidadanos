# Gestión de Expedientes de Ciudadanos con Microservicios

Este proyecto es una aplicación de gestión de expedientes de ciudadanos desarrollada con microservicios utilizando Java 8, Spring Boot, Maven, Swagger, entre otras tecnologías.

## Descripción

El objetivo de este proyecto es proporcionar una solución escalable y modular para la gestión de expedientes de ciudadanos. 
Utilizando arquitectura de microservicios, cada funcionalidad está encapsulada en un servicio independiente, lo que facilita el mantenimiento, la escalabilidad y la colaboración entre equipos de desarrollo.

## Tecnologías Utilizadas

- Java 8
- Spring Boot
- Maven
- Swagger

## Estructura del Proyecto

El proyecto está estructurado en varios microservicios, cada uno con una responsabilidad específica. A continuación se detallan los microservicios principales:

- **Servicio de Registro y discovery**: Maneja la conexión entre varios microservicios.
- **Servicio de Expedientes**: Gestiona la creación, actualización y consulta de expedientes de ciudadanos.
- **Servicio de Ciudadanos**: Administra la información de los ciudadanos del sistema.

## Configuración y Ejecución

1. Clona el repositorio desde GitHub:

```bash
git clone https://github.com/jmetene/cuidadanos.git
```

2. Navega al directorio raíz del proyecto:

```bash
cd gestion-expedientes-microservicios
```

3. Compila el proyecto utilizando Maven:

```bash
mvn clean install
```

4. Ejecuta cada microservicio de forma individual. Por ejemplo, para ejecutar el servicio de Expedientes:

```bash
cd [nombre-microservicio]
mvn spring-boot:run
```

5. Accede a la documentación de la API utilizando Swagger. Una vez que los microservicios estén en ejecución, visita la siguiente URL en tu navegador:

```
http://localhost:[puerto]/swagger-ui.html
```

## Contribución

¡Contribuciones son bienvenidas! Si deseas contribuir al proyecto, por favor sigue los siguientes pasos:

1. Realiza un fork del repositorio.
2. Crea una nueva rama para tu funcionalidad (git checkout -b feature/nueva-funcionalidad).
3. Realiza tus cambios y haz commit de ellos (git commit -am 'Agrega nueva funcionalidad').
4. Haz push de tu rama (git push origin feature/nueva-funcionalidad).
5. Crea un nuevo pull request.

## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).

---
