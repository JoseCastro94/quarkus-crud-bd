CREATE DATABASE BD_empleado;
GO

USE BD_empleado;
GO

CREATE TABLE dbo.empleados (
    id INT IDENTITY(1, 1) NOT NULL,
    nombres NVARCHAR(100) NOT NULL,
    apellidos NVARCHAR(100) NOT NULL,
    salario DECIMAL(12, 2) NOT NULL,
    fecha_registro DATETIME2(6) NOT NULL,
    CONSTRAINT PK_empleados PRIMARY KEY (id),
    CONSTRAINT CK_empleados_salario_no_negativo CHECK (salario >= 0)
);

select * from empleados