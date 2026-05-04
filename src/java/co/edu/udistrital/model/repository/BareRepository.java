package co.edu.udistrital.model.repository;

import java.util.List;

/**
 * Plantilla genérica para el acceso a datos. Base de los repositorios CRUD del
 * sistema. Asegura la carga del controlador de Base de Datos y provee métodos
 * comunes e indispensables para procesar registros.
 *
 * @param <T> El tipo de entidad o DTO que manipulará la implementación.
 *
 * @since 0.1
 * @author Manuel Salazar
 */
public abstract class BareRepository<T> {

    static {
        try {
            Class.forName(Config.DRIVER);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Error: No se encontró el driver de MariaDB.");
            e.printStackTrace();
        }
    }

    /**
     * Metodo interno para convertir identificadores alfanuméricos en numéricos
     * para las consultas (Quita prefijos). Especialmente diseñado para limpiar
     * las PK como "Cl0001", "Pr002" etc.
     *
     * @param id Identificador en bruto con texto a limpiar.
     *
     * @return La id numérica extraída para sentencias SQL.
     */
    private int parseId(String id) {
        return Integer.parseInt(id.replaceAll("[^0-9]", ""));
    }

    /**
     * Persiste una entidad en su tabla correspondiente de la base de datos.
     *
     * @param entity El objeto debidamente instanciado conteniendo la data a
     *               insertar.
     *
     * @return {@code true} si la operación modificó las filas con éxito,
     *         {@code false} de lo contrario.
     */
    public abstract boolean add(T entity);

    /**
     * Recupera una entidad por medio de su identificador único.
     *
     * @param id El string representativo del registro (Ej: Em0002).
     *
     * @return El objeto instanciado o null si no hubo conincidencia.
     */
    public abstract T getById(String id);

    /**
     * Obtiene el listado completo de los registros persistidos para la entidad.
     *
     * @return Colección en forma de lista de las entidades encontradas.
     */
    public abstract List<T> getAll();

    /**
     * Calcula la volumetría real o actual en la tabla principal tratada.
     *
     * @return Número de filas presentes en la entidad consultada.
     */
    public abstract int cantidad();
}
