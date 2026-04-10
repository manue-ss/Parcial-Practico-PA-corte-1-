package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Pelicula;
import co.edu.udistrital.util.LocalDateAdapter;
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
 * Repositorio enfocado a persistencia y acceso de datos para Películas
 * independientemente.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class PeliculaRepository implements IRepository<Pelicula, String> {

    private static final String FILE_PATH = "peliculas.json";
    private Map<String, Pelicula> map;
    private Gson gson;

    public PeliculaRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.map = new HashMap<>();
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Pelicula>>() {
            }.getType();
            List<Pelicula> list = gson.fromJson(reader, listType);
            if (list != null) {
                for (Pelicula e : list) {
                    map.put(e.getId(), e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar peliculas: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        List<Pelicula> list = new ArrayList<>(map.values());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar peliculas: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Pelicula entity) {
        map.put(entity.getId(), entity);
        guardarEnArchivo();
    }

    @Override
    public Pelicula obtenerPorId(String id) {
        return map.get(id);
    }

    @Override
    public List<Pelicula> obtenerTodos() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean eliminar(String id) {
        Pelicula rem = map.remove(id);
        if (rem != null) {
            guardarEnArchivo();
            return true;
        }
        return false;
    }

    @Override
    public int cantidad() {
        return map.size();
    }
}
