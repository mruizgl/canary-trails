package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Fauna en el sistema.
 * Esta clase está mapeada a la tabla "faunas" en la base de datos.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "faunas")
public class Fauna {

    /**
     * Identificador único de la fauna.
     * Generado automáticamente con el autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la fauna.
     */
    private String nombre;

    /**
     * Descripción detallada de la fauna.
     * Almacenada como texto en la base de datos.
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Indica si la fauna está aprobada.
     */
    private Boolean aprobada;

    /**
     * Lista de usuarios asociados a esta fauna.
     * Relación de muchos a muchos mapeada por el atributo "faunas" en la entidad Usuario.
     */
    @ManyToMany(mappedBy = "faunas")
    private List<Usuario> usuarios;

    /**
     * Lista de rutas asociadas a esta fauna.
     * Relación de muchos a muchos mapeada por el atributo "faunas" en la entidad Ruta.
     */
    @ManyToMany(mappedBy = "faunas")
    private List<Ruta> rutas;

    /**
     * Obtiene el identificador único de la fauna.
     * @return el identificador de la fauna.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único de la fauna.
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la fauna.
     * @return el nombre de la fauna.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la fauna.
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la descripción de la fauna.
     * @return la descripción de la fauna.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la fauna.
     * @param descripcion la descripción a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado de aprobación de la fauna.
     * @return true si está aprobada, false en caso contrario.
     */
    public Boolean getAprobada() {
        return aprobada;
    }

    /**
     * Establece el estado de aprobación de la fauna.
     * @param aprobada el estado de aprobación a establecer.
     */
    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    /**
     * Obtiene la lista de usuarios asociados a esta fauna.
     * @return la lista de usuarios.
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Establece la lista de usuarios asociados a esta fauna.
     * @param usuarios la lista de usuarios a establecer.
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    /**
     * Obtiene la lista de rutas asociadas a esta fauna.
     * @return la lista de rutas.
     */
    public List<Ruta> getRutas() {
        return rutas;
    }

    /**
     * Establece la lista de rutas asociadas a esta fauna.
     * @param rutas la lista de rutas a establecer.
     */
    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    /**
     * Compara esta fauna con otro objeto para determinar si son iguales.
     * Dos faunas son iguales si tienen el mismo identificador.
     * @param o el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fauna fauna = (Fauna) o;
        return Objects.equals(id, fauna.id);
    }

    /**
     * Calcula el código hash de la fauna basado en su identificador.
     * @return el código hash de la fauna.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena de la fauna.
     * @return una cadena que representa la fauna.
     */
    @Override
    public String toString() {
        return "Fauna{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aprobada=" + aprobada +
                ", usuarios=" + usuarios +
                ", rutas=" + rutas +
                '}';
    }
}