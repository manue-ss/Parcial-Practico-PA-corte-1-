/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.alquiler.model.repository;

import com.alquiler.model.entities.Cliente;
import com.alquiler.util.LocalDateAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repositorio para gestionar clientes usando un archivo JSON. La lista se
 * guarda como arreglo JSON, pero internamente se usa un Map para búsquedas
 * rápidas.
 *
 * @author Acer-Pc
 */
public class ClienteRepository {

    private static final String FILE_PATH = "clientes.json";
    private Map<String, Cliente> clientesMap;
    private Gson gson;

    public ClienteRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.clientesMap = new HashMap<>();
        cargarDesdeArchivo();
    }

    /**
     * Carga la lista de clientes desde el archivo JSON.
     */
    private void cargarDesdeArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // Si no existe, empezar con mapa vacío
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Cliente>>() {
            }.getType();
            List<Cliente> clientesList = gson.fromJson(reader, listType);
            if (clientesList != null) {
                for (Cliente cliente : clientesList) {
                    clientesMap.put(cliente.getNombreUsuario(), cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar clientes desde archivo: " + e.getMessage());
        }
    }

    /**
     * Guarda la lista de clientes en el archivo JSON.
     */
    private void guardarEnArchivo() {
        List<Cliente> clientesList = new ArrayList<>(clientesMap.values());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(clientesList, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar clientes en archivo: " + e.getMessage());
        }
    }

    /**
     * Guarda un nuevo cliente. Si ya existe, lo actualiza.
     *
     * @param cliente El cliente a guardar.
     */
    public void guardar(Cliente cliente) {
        clientesMap.put(cliente.getNombreUsuario(), cliente);
        guardarEnArchivo();
    }

    /**
     * Obtiene un cliente por nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return El cliente o null si no existe.
     */
    public Cliente obtenerPorUsername(String nombreUsuario) {
        return clientesMap.get(nombreUsuario);
    }

    /**
     * Obtiene un cliente por correo.
     *
     * @param correo El correo.
     * @return El cliente o null si no existe.
     */
    public Cliente obtenerPorCorreo(String correo) {
        for (Cliente cliente : clientesMap.values()) {
            if (cliente.getCorreo().equals(correo)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Verifica si existe un cliente con el nombre de usuario dado.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return true si existe, false en caso contrario.
     */
    public boolean existeUsername(String nombreUsuario) {
        return clientesMap.containsKey(nombreUsuario);
    }

    /**
     * Verifica si existe un cliente con el correo dado.
     *
     * @param correo El correo.
     * @return true si existe, false en caso contrario.
     */
    public boolean existeCorreo(String correo) {
        return obtenerPorCorreo(correo) != null;
    }

    /**
     * Obtiene todos los clientes.
     *
     * @return Lista de todos los clientes.
     */
    public List<Cliente> obtenerTodos() {
        return new ArrayList<>(clientesMap.values());
    }

    /**
     * Elimina un cliente por nombre de usuario.
     *
     * @param nombreUsuario El nombre de usuario.
     * @return true si se eliminó, false si no existía.
     */
    public boolean eliminar(String nombreUsuario) {
        Cliente removed = clientesMap.remove(nombreUsuario);
        if (removed != null) {
            guardarEnArchivo();
            return true;
        }
        return false;
    }

    public int cantidadClientes() {
        return clientesMap.size();
    }
}
