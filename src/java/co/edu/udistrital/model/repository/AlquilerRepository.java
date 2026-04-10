package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Alquiler;
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
 * Repositorio de transacciones. Gestiona los metadatos de renta y vincula
 * a usuarios con productos a través de claves foráneas simuladas.
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class AlquilerRepository implements IRepository<Alquiler, String> {

    private static final String FILE_PATH = "alquileres.json";
    private Map<String, Alquiler> map;
    private Gson gson;

    public AlquilerRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.map = new HashMap<>();
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Alquiler>>() {}.getType();
            List<Alquiler> list = gson.fromJson(reader, listType);
            if (list != null) {
                for (Alquiler e : list) {
                    map.put(e.getIdAlquiler(), e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar alquileres: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        List<Alquiler> list = new ArrayList<>(map.values());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar alquileres: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Alquiler entity) {
        map.put(entity.getIdAlquiler(), entity);
        guardarEnArchivo();
    }

    @Override
    public Alquiler obtenerPorId(String id) {
        return map.get(id);
    }

    @Override
    public List<Alquiler> obtenerTodos() {
        return new ArrayList<>(map.values());
    }

    @Override
    public boolean eliminar(String id) {
        Alquiler rem = map.remove(id);
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

    /**
     * Construye un arreglo transitorio iterando el mapa central para agrupar
     * los alquileres que pertenecen únicamente a un cliente en base a su ID.
     * 
     * @param idUsuario Identificador único del cliente consultante.
     * @return Lista segregada de alquileres de la persona.
     */
    public List<Alquiler> obtenerPorUsuario(String idUsuario) {
        List<Alquiler> deUsuario = new ArrayList<>();
        for (Alquiler a : map.values()) {
            if (a.getIdUsuario()!= null && a.getIdUsuario().equals(idUsuario)) {
                deUsuario.add(a);
            }
        }
        return deUsuario;
    }
}
