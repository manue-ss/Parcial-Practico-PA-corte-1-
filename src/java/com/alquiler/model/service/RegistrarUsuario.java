package com.alquiler.model.service;

import com.alquiler.model.dto.ClienteDTO;
import com.alquiler.model.entities.Cliente;
import com.alquiler.model.repository.ClienteRepository;
import com.alquiler.util.ClienteMapper;
import com.alquiler.util.SecurityUtil;

public class RegistrarUsuario {

    private ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes.
     */
    public RegistrarUsuario(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void ejecutar(ClienteDTO cliente) {
        String passPlana = cliente.getContrasenia();
        String passHasheada = SecurityUtil.encriptarSHA256(passPlana);
        Cliente nuevoCliente;

        cliente.setContrasenia(passHasheada);
        String id = "CL" + String.format("%04d", repositorio.cantidadClientes() + 1);
        cliente.setId(id);

        nuevoCliente = ClienteMapper.toEntity(cliente);

        repositorio.guardar(nuevoCliente);
    }
}
