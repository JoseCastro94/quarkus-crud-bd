# quarkus-crud-bd

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## API de empleados

El CRUD está disponible en `/api/empleados` y persiste los datos en SQL Server mediante Hibernate ORM con Panache.

| Método | Ruta | Descripción |
| --- | --- | --- |
| `GET` | `/api/empleados` | Lista todos los empleados |
| `GET` | `/api/empleados/{id}` | Busca un empleado por ID entero |
| `POST` | `/api/empleados` | Registra un empleado |
| `PUT` | `/api/empleados/{id}` | Actualiza un empleado |
| `DELETE` | `/api/empleados/{id}` | Elimina un empleado |

La interfaz interactiva Swagger UI está disponible en `http://localhost:8080/q/swagger-ui/` y la especificación OpenAPI en `http://localhost:8080/q/openapi`.

Los requests de creación y actualización requieren `nombres` y `apellidos` no vacíos (máximo 100 caracteres), y un `salario` obligatorio mayor o igual a cero. Los campos se validan con Jakarta Bean Validation y muestran mensajes en español; los requests inválidos responden con HTTP `400`. El DTO usa Lombok para generar getters, setters y constructor vacío.

### Configuración de SQL Server

Ejecuta [database/sqlserver.sql](./database/sqlserver.sql) en SQL Server para crear la base `empleados` y la tabla `dbo.empleados`. La aplicación espera SQL Server en `localhost:1433`, con usuario `sa` y contraseña `castro`; puedes cambiar estos valores mediante `DB_JDBC_URL`, `DB_USERNAME` y `DB_PASSWORD`. En producción la aplicación valida que la tabla exista y no la crea automáticamente.

Los tests usan una base H2 aislada en modo de compatibilidad con SQL Server; no requieren una instancia externa de SQL Server.

La colección de Postman está en [empleados.postman_collection.json](./empleados.postman_collection.json). Importa el archivo en Postman, inicia la aplicación y ejecuta las solicitudes CRUD en orden; la solicitud de registro guarda automáticamente el ID entero para las siguientes.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-crud-bd-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST Jackson ([guide](https://quarkus.io/guides/rest#json-serialisation)): Jackson serialization support for Quarkus REST. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)
