/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.edu.udistrital.model.service;

import co.edu.udistrital.model.dto.AlquilerDTO;
import co.edu.udistrital.model.dto.ProductoDTO;
import co.edu.udistrital.model.entities.Alquiler;
import co.edu.udistrital.model.entities.Juego;
import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.model.repository.AlquilerRepository;
import co.edu.udistrital.model.repository.JuegoRepository;
import co.edu.udistrital.model.repository.PeliculaRepository;
import co.edu.udistrital.util.AlquilerMapper;
import co.edu.udistrital.util.ProductoMapper;
import java.util.ArrayList;
import java.util.List;

/**
 * Servicio encargado de la logística del Catálogo y la consolidación de Alquileres.
 * Aplica lógica de negocio para obtener "Novedades" mixtas entre películas
 * y juegos, y cruza datos entre los dominios de Alquileres y Productos.
 *
 * @author Manuel Salazar
 * @since 0.2
 */
public class CatalogoService {

    private final JuegoRepository jr;
    private final PeliculaRepository pr;
    private final AlquilerRepository ar;

    /**
     * Construye un CatalogoService con inyección de dependencias.
     *
     * @param jr Dependencia repositorio de Juegos.
     * @param pr Dependencia repositorio de Películas.
     * @param ar Dependencia repositorio de Alquileres.
     */
    public CatalogoService(JuegoRepository jr, PeliculaRepository pr, AlquilerRepository ar) {
        this.jr = jr;
        this.pr = pr;
        this.ar = ar;
    }

    /**
     * Construye una lista integrada de DTOs con las últimas novedades del sistema,
     * obteniendo progresivamente una película y un juego para proveer variedad visual
     * a las vitrinas front-end (Store).
     *
     * @param max Límite de ítems que pueden ser retornados combinando ambos.
     * @return Una lista de {@link ProductoDTO} listos para iterar el JSP.
     */
    public List<ProductoDTO> getNovedades(int max) {

        List<Juego> listaJuegos = jr.getAll().reversed();
        List<Pelicula> listaPeliculas = pr.getAll().reversed();

        List<ProductoDTO> novedades = new ArrayList<>();

        int totalDisponible = listaJuegos.size() + listaPeliculas.size();
        int lim = Math.min(max, totalDisponible);
        int maxIteraciones = Math.max(listaJuegos.size(), listaPeliculas.size());

        for (int i = 0; i < maxIteraciones && novedades.size() < lim; i++) {

            if (i < listaJuegos.size() && novedades.size() < lim) {
                Juego jg = listaJuegos.get(i);
                ProductoDTO dto = ProductoMapper.toDTO(jg);
                dto.setCategoria("Juego");
                dto.setDetalle(jg.getGenero());
                dto.setPlataforma(jg.getPlataforma());
                novedades.add(dto);
            }

            // Agregar Película si existe en esta posición
            if (i < listaPeliculas.size() && novedades.size() < lim) {
                Pelicula pl = listaPeliculas.get(i);
                ProductoDTO dto = ProductoMapper.toDTO(pl);
                dto.setCategoria("Película");
                dto.setDetalle(pl.getDuracion());
                dto.setPlataforma(pl.getFormato());
                novedades.add(dto);
            }
        }

        return novedades;
    }

    /**
     * Obtiene el registro completo de alquileres de un cliente determinado, 
     * integrando manualmente la información del producto asociado a ese alquiler.
     *
     * @param clienteId El string identificador del cliente en concreto.
     * @return Lista de Alquileres transformados a DTO.
     */
    public List<AlquilerDTO> getAlquileresDetallados(String clienteId) {
        List<Alquiler> misAlquileres = ar.getByCustomer(clienteId);
        List<AlquilerDTO> listaDto = new ArrayList<>();

        for (Alquiler a : misAlquileres) {
            AlquilerDTO dto = AlquilerMapper.toDTO(a);

            // Esta lógica de identificación de prefijo vive mejor aquí
            if (a.getIdProducto().startsWith("Jg")) {
                Juego j = jr.getById(a.getIdProducto());
                dto.setNombreProducto(j != null ? j.getNombreProducto() : "Juego no encontrado");
                dto.setTipoProducto("Juego");
            } else {
                Pelicula p = pr.getById(a.getIdProducto());
                dto.setNombreProducto(p != null ? p.getNombreProducto() : "Película no encontrada");
                dto.setTipoProducto("Película");
            }
            listaDto.add(dto);
        }
        return listaDto;
    }

    /**
     * Recupera un listado en formato resumido que concentra los alquileres 
     * en estado 'ACTIVO' o no resueltos por el cliente.
     *
     * @param clienteId Identificador de cuenta de dicho cliente.
     * @return Listado de AlquilerDTO simplificado con el string conformado ("Plataforma · Género/Duración")
     */
    public List<AlquilerDTO> getAlquileresVigentes(String clienteId) {
        List<Alquiler> todos = ar.getByCustomer(clienteId);
        List<AlquilerDTO> vigentesDto = new ArrayList<>();

        for (Alquiler a : todos) {
            AlquilerDTO dto = AlquilerMapper.toDTO(a);

            // Lógica de títulos y detalles centralizada
            if (a.getIdProducto().startsWith("Jg")) {
                Juego j = jr.getById(a.getIdProducto());
                if (j != null) {
                    dto.setNombreProducto(j.getNombreProducto());
                    dto.setTipoProducto(j.getPlataforma() + " · " + j.getGenero());
                }
            } else {
                Pelicula p = pr.getById(a.getIdProducto());
                if (p != null) {
                    dto.setNombreProducto(p.getNombreProducto());
                    dto.setTipoProducto(p.getFormato() + " · " + p.getDuracion());
                }
            }
            vigentesDto.add(dto);

        }
        return vigentesDto;
    }
}
