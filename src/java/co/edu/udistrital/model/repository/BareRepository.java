/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model.repository;

import java.util.List;

/**
 *
 * @author Acer-Pc
 */
public abstract class BareRepository<T> {

    static {
        try {
            Class.forName(Config.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MariaDB.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo para limpiar Id's.
     *
     * @param id Identificador a limpiar
     * @return La id numerica limpia.
     */
    private int parseId(String id) {
        return Integer.parseInt(id.replaceAll("[^0-9]", ""));
    }

    public abstract boolean add(T entity);

    public abstract T getById(String id);

    public abstract List<T> getAll();

    public abstract int cantidad();
}
