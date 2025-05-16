package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Zona en el sistema.
 * Esta clase está mapeada a la tabla "zonas" en la base de datos.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "zonas")
public class Zona {

    /**
     * Identificador único de la zona.
     * Generado automáticamente con un autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la zona.
     */
    private String nombre;

    /**
     * Lista de municipios asociados a la zona.
     * Relación de uno a muchos mapeada por el atributo "zona" en la entidad Municipio.
     */
    @OneToMany(mappedBy = "zona")
    private List<Municipio> municipios;

    public Zona() {
        this.municipios = new ArrayList<>();
    }

    /**
     * Obtiene el identificador único de la zona.
     * @return el identificador de la zona.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único de la zona.
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la zona.
     * @return el nombre de la zona.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la zona.
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la lista de municipios asociados a la zona.
     * @return la lista de municipios.
     */
    public List<Municipio> getMunicipios() {
        return municipios;
    }

    /**
     * Establece la lista de municipios asociados a la zona.
     * @param municipios la lista de municipios a establecer.
     */
    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    /**
     * Compara esta zona con otro objeto para determinar si son iguales.
     * Dos zonas son iguales si tienen el mismo identificador.
     * @param o el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Zona zona = (Zona) o;
        return Objects.equals(id, zona.id);
    }

    /**
     * Calcula el código hash de la zona basado en su identificador.
     * @return el código hash de la zona.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena de la zona.
     * @return una cadena que representa la zona, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "Zona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", municipios=" + municipios +
                '}';
    }
}