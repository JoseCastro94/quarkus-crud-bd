package com.app.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.app.model.Empleado;
import com.app.model.dto.EmpleadoRequest;
import com.app.repository.EmpleadoRepository;

@ApplicationScoped
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public List<Empleado> listar() {
        return empleadoRepository.listAll();
    }

    public Optional<Empleado> buscarPorId(Integer id) {
        return empleadoRepository.findByIdOptional(id);
    }

    @Transactional
    public Empleado registrar(EmpleadoRequest request) {
        Empleado empleado = Empleado.builder()
                .nombres(request.getNombres())
                .apellidos(request.getApellidos())
                .salario(request.getSalario())
                .fechaRegistro(LocalDateTime.now())
                .build();

        empleadoRepository.persist(empleado);
        return empleado;
    }

    @Transactional
    public Optional<Empleado> actualizar(Integer id, EmpleadoRequest request) {
        Optional<Empleado> empleadoEncontrado = buscarPorId(id);
        empleadoEncontrado.ifPresent(empleado -> {
            empleado.setNombres(request.getNombres());
            empleado.setApellidos(request.getApellidos());
            empleado.setSalario(request.getSalario());
        });
        return empleadoEncontrado;
    }

    @Transactional
    public boolean eliminar(Integer id) {
        return empleadoRepository.deleteById(id);
    }
}