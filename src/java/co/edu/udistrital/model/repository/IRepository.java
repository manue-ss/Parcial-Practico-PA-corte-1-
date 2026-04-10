package co.edu.udistrital.model.repository;

import java.util.List;

/**
 * Interfaz genérica para abstraer las operaciones de los repositorios del
 * sistema, cumpliendo el principio ISP (Segregación de Interfaces).
 *
 * @author Manuel Salazar
 * @since 0.1
 * @param <T> Tipo de Entidad a persistir.
 * @param <ID> Tipo de dato del identificador primario.
 */
public interface IRepository<T, ID> {

    /**
     * Almacena o actualiza la entidad proveniente en el repositorio de datos.
     *
     * @param entity La entidad de tipo T a guardar.
     */
    void guardar(T entity);

    /**
     * Busca la entidad única identificada mediante su clave primaria.
     *
     * @param id Identificador objeto a buscar.
     * @return T en caso de ser encontrada o null en caso contrario.
     */
    T obtenerPorId(ID id);

    /**
     * Recupera todas las entidades de este sub-sistema en forma iterativa de
     * Lista.
     *
     * @return Una colección List<T> con las entidades vigentes.
     */
    List<T> obtenerTodos();

    /**
     * Elimina el registro del repositorio en base a la clave proporcionada.
     *
     * @param id Clave identificadora a erradicar del sistema.
     * @return verdadero si fue procesado, falso en caso contrario.
     */
    boolean eliminar(ID id);

    /**
     * Contabiliza los objetos que actualmente son gestionados por el
     * repositorio.
     *
     * @return Número entero con el tamaño o cantidad de transacciones
     * disponibles.
     */
    int cantidad();
}
