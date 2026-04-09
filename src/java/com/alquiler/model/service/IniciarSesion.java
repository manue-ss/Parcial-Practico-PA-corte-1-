/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alquiler.model.service;

import com.alquiler.model.dto.ClienteDTO;
import com.alquiler.model.entities.Cliente;
import com.alquiler.model.repository.ClienteRepository;
import com.alquiler.util.SecurityUtil;

/**
 *
 * @author Acer-Pc
 */
public class IniciarSesion {

    private ClienteRepository repositorio;

    /**
     * Constructor que recibe el repositorio inyectado.
     *
     * @param repositorio El repositorio de clientes.
     */
    public IniciarSesion(ClienteRepository repositorio) {
        this.repositorio = repositorio;
    }

    public Cliente ejecutar(ClienteDTO dto) {
        Cliente cliente;
        String identificador = dto.getNombreUsuario();

        if (identificador.contains("@")) {
            cliente = repositorio.obtenerPorCorreo(identificador);
        } else {
            cliente = repositorio.obtenerPorUsername(identificador);
        }

        if (cliente == null) {
            return null;
        }

        String passHasheada = SecurityUtil.encriptarSHA256(dto.getContrasenia());

        if (cliente.getContrasenia().equals(passHasheada)) {
            return cliente;
        }

        return null;
    }
}
