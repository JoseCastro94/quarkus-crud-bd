package com.app.repository;

import com.app.model.Empleado;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EmpleadoRepository implements PanacheRepositoryBase<Empleado, Integer> {
}
