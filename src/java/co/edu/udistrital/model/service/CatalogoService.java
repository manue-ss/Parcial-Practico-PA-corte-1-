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
 *
 * @author Acer-Pc
 */
public class CatalogoService {

    private final JuegoRepository jr;
    private final PeliculaRepository pr;
    private final AlquilerRepository ar;

    public CatalogoService(JuegoRepository jr, PeliculaRepository pr, AlquilerRepository ar) {
        this.jr = jr;
        this.pr = pr;
        this.ar = ar;
    }

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
}
