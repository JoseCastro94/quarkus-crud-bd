package com.app.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.app.model.dto.EmpleadoRequest;
import com.app.service.EmpleadoService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/api/empleados")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Empleados", description = "Operaciones CRUD para empleados")
public class EmpleadoResource {

    @Inject
    private EmpleadoService empleadoService;

    @GET
    @Operation(summary = "Listar empleados")
    @APIResponse(responseCode = "200", description = "Lista de empleados")
    public Response listar() {
        return Response.ok(empleadoService.listar()).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Buscar empleado por ID")
    @APIResponse(responseCode = "200", description = "Empleado encontrado")
    @APIResponse(responseCode = "404", description = "No existe un empleado con ese ID")
    public Response buscarPorId(@PathParam("id") Integer id) {
        return empleadoService.buscarPorId(id)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    @Operation(summary = "Registrar empleado")
    @APIResponse(responseCode = "201", description = "Empleado creado")
    @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")
    public Response registrar(@Valid @NotNull EmpleadoRequest request) {
        return Response.status(Response.Status.CREATED)
                .entity(empleadoService.registrar(request))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Actualizar empleado")
    @APIResponse(responseCode = "200", description = "Empleado actualizado")
    @APIResponse(responseCode = "400", description = "Datos de entrada inválidos")
    @APIResponse(responseCode = "404", description = "No existe un empleado con ese ID")
    public Response actualizar(
            @PathParam("id") Integer id,
            @Valid @NotNull EmpleadoRequest request) {

        return empleadoService.actualizar(id, request)
                .map(Response::ok)
                .map(Response.ResponseBuilder::build)
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar empleado")
    @APIResponse(responseCode = "204", description = "Empleado eliminado")
    @APIResponse(responseCode = "404", description = "No existe un empleado con ese ID")
    public Response eliminar(@PathParam("id") Integer id) {
        if (!empleadoService.eliminar(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.noContent().build();
    }
}