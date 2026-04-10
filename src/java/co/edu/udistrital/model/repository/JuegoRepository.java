package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Juego;
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
 * Repositorio enfocado a persistencia y acceso de datos para Películas.
 * Implementa el principio Abierto/Cerrado (OCP) al dividir el acceso de
 * productos por su clase concreta.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class JuegoRepository implements IRepository<Juego, String> {

    private static final String FILE_PATH = "juegos.json";
    private Map<String, Juego> map;
    private Gson gson;

    public JuegoRepository() {
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
            Type listType = new TypeToken<List<Juego>>() {
            }.getType();
            List<Juego> list = gson.fromJson(reader, listType);
            if (list != null) {
                for (Juego e : list) {
                    map.put(e.getId(), e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar juegos: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        List<Juego> list = new ArrayList<>(map.values());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar juegos: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Juego entity) {
        map.put(entity.getId(), entity);
        guardarEnArchivo();
    }

    @Override
    public Juego obtenerPorId(String id) {
        return map.get(id);
    }

    @Override
    public List<Juego> obtenerTodos() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean eliminar(String id) {
        Juego rem = map.remove(id);
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
