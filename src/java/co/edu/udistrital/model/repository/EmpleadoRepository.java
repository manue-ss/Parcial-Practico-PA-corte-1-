package co.edu.udistrital.model.repository;

import co.edu.udistrital.model.entities.Empleado;
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
 * Repositorio encargado de gestionar la persistencia de Empleados y
 * Administradores usando un archivo JSON dedicado.
 *
 * Implementa IRepository para cumplir con el Principio de Segregación de
 * Interfaces (ISP).
 *
 * @author Manuel Salazar
 * @since 0.1
 */
public class EmpleadoRepository implements IRepository<Empleado, String> {

    private static final String FILE_PATH = "empleados.json";
    private Map<String, Empleado> empleadosMap;
    private Gson gson;

    public EmpleadoRepository() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        this.empleadosMap = new HashMap<>();
        cargarDesdeArchivo();
    }

    private void cargarDesdeArchivo() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }
        try (FileReader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Empleado>>() {
            }.getType();
            List<Empleado> emps = gson.fromJson(reader, listType);
            if (emps != null) {
                for (Empleado e : emps) {
                    empleadosMap.put(e.getNombreUsuario(), e);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al cargar empleados: " + e.getMessage());
        }
    }

    private void guardarEnArchivo() {
        List<Empleado> list = new ArrayList<>(empleadosMap.values());
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            gson.toJson(list, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar empleados: " + e.getMessage());
        }
    }

    @Override
    public void guardar(Empleado empleado) {
        empleadosMap.put(empleado.getNombreUsuario(), empleado);
        guardarEnArchivo();
    }

    @Override
    public Empleado obtenerPorId(String id) {
        for (Empleado empleados : empleadosMap.values()) {
            if (empleados.getId().equals(id)) {
                return empleados;
            }
        }
        return null;
    }

    /**
     * Retorna el empleado si sus credenciales son válidas.
     *
     * @param username string de identificación
     * @return instanciación del objeto, null si no hay.
     */
    public Empleado obtenerPorUsername(String username) {
        return empleadosMap.get(username);
    }

    public Empleado obtenerPorCorreo(String correo) {
        for (Empleado empleado : empleadosMap.values()) {
            if (empleado.getCorreo().equals(correo)) {
                return empleado;
            }
        }
        return null;
    }
    @Override
    public List<Empleado> obtenerTodos() {
        return new ArrayList<>(empleadosMap.values());
    }

    @Override
    public boolean eliminar(String username) {
        Empleado rem = empleadosMap.remove(username);
        if (rem != null) {
            guardarEnArchivo();
            return true;
        }
        return false;
    }

    @Override
    public int cantidad() {
        return empleadosMap.size();
    }

}
