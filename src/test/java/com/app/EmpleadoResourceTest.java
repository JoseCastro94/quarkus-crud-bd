package com.app;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

@QuarkusTest
class EmpleadoResourceTest {

    @Test
    void realizaOperacionesCrudYValidaLosRequests() {
        Integer empleadoCreado = given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nombres": "Ana",
                          "apellidos": "Lopez",
                          "salario": 2500.50
                        }
                        """)
                .when()
                .post("/api/empleados")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("nombres", equalTo("Ana"))
                .extract()
                .path("id");

        given()
                .when()
                .get("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(200)
                .body("apellidos", equalTo("Lopez"));

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nombres": "Ana Maria",
                          "apellidos": "Lopez",
                          "salario": 3000
                        }
                        """)
                .when()
                .put("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(200)
                .body("nombres", equalTo("Ana Maria"))
                .body("salario", equalTo(3000));

        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nombres": " ",
                          "apellidos": "",
                          "salario": -1
                        }
                        """)
                .when()
                .put("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(400);

        given()
                .when()
                .get("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(200)
                .body("nombres", equalTo("Ana Maria"));

        given()
                .when()
                .delete("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/api/empleados/{id}", empleadoCreado)
                .then()
                .statusCode(404);
    }

    @Test
    void rechazaRequestsConCamposObligatoriosAusentes() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                          "nombres": "Carlos",
                          "apellidos": "Perez"
                        }
                        """)
                .when()
                .post("/api/empleados")
                .then()
                .statusCode(400);
    }

    @Test
    void exponeDocumentacionOpenApi() {
        given()
                .accept("application/json")
                .when()
                .get("/q/openapi?format=json")
                .then()
                .statusCode(200)
                .body("info.title", equalTo("API de Empleados"))
                .body("paths.'/api/empleados'.get", notNullValue())
                .body("paths.'/api/empleados'.post", notNullValue());

        given()
                .when()
                .get("/q/swagger-ui/")
                .then()
                .statusCode(200)
                .contentType("text/html");
    }
}
