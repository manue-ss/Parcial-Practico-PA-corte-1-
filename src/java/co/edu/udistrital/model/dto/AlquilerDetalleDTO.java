/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model.dto;

import co.edu.udistrital.model.entities.Alquiler;

/**
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerDetalleDTO {
    private Alquiler alquiler;
    private String nombreProducto;
    private String tipoProducto; // "Juego" o "Película"

    public AlquilerDetalleDTO(Alquiler alquiler, String nombreProducto, String tipoProducto) {
        this.alquiler = alquiler;
        this.nombreProducto = nombreProducto;
        this.tipoProducto = tipoProducto;
    }

    // Getters
    public Alquiler getAlquiler() { return alquiler; }
    public String getNombreProducto() { return nombreProducto; }
    public String getTipoProducto() { return tipoProducto; }
}

