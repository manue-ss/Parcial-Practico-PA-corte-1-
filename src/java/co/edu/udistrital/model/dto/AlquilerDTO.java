package co.edu.udistrital.model.dto;

import java.time.LocalDate;

/**
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class AlquilerDTO {

    private String nombreProducto;
    private String tipoProducto;
    private String idAlquiler;
    private String idCliente;
    private String idProducto;
    private LocalDate fechaAlquiler;
    private LocalDate fechaPactada;
    private double costoTotal;
    private String estado;

    public AlquilerDTO() {
        this.nombreProducto = "";
        this.tipoProducto = "";
        this.idAlquiler = "";
        this.costoTotal = 0.0;
        this.estado = "";
        this.fechaAlquiler = null;
        this.fechaPactada = null;
        this.idAlquiler = "";
        this.idCliente = "";
        this.idProducto = "";
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getIdAlquiler() {
        return idAlquiler;
    }

    public void setIdAlquiler(String idAlquiler) {
        this.idAlquiler = idAlquiler;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public LocalDate getFechaAlquiler() {
        return fechaAlquiler;
    }

    public void setFechaAlquiler(LocalDate fechaAlquiler) {
        this.fechaAlquiler = fechaAlquiler;
    }

    public LocalDate getFechaPactada() {
        return fechaPactada;
    }

    public void setFechaPactada(LocalDate fechaPactada) {
        this.fechaPactada = fechaPactada;
    }

    public double getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    
}
