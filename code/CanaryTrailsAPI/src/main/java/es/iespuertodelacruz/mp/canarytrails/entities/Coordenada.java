package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una coordenada geográfica con latitud y longitud,
 * asociada a una o más rutas en el sistema.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "coordenadas")
public class Coordenada {

    /**
     * Identificador único de la coordenada.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Latitud de la coordenada.
     * Precisión de 10 dígitos con 6 decimales.
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal latitud;

    /**
     * Longitud de la coordenada.
     * Precisión de 10 dígitos con 6 decimales.
     */
    @Column(precision = 10, scale = 6)
    private BigDecimal longitud;

    /**
     * Lista de rutas asociadas a esta coordenada.
     * Relación ManyToMany con la entidad Ruta.
     */
    @ManyToMany(mappedBy = "coordenadas")
    private List<Ruta> rutas;

    public Coordenada() {
        this.rutas = new ArrayList<>();
    }

    /**
     * Obtiene el identificador de la coordenada.
     *
     * @return el identificador de la coordenada.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador de la coordenada.
     *
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene la latitud de la coordenada.
     *
     * @return la latitud de la coordenada.
     */
    public BigDecimal getLatitud() {
        return latitud;
    }

    /**
     * Establece la latitud de la coordenada.
     *
     * @param latitud la latitud a establecer.
     */
    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    /**
     * Obtiene la longitud de la coordenada.
     *
     * @return la longitud de la coordenada.
     */
    public BigDecimal getLongitud() {
        return longitud;
    }

    /**
     * Establece la longitud de la coordenada.
     *
     * @param longitud la longitud a establecer.
     */
    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    /**
     * Obtiene la lista de rutas asociadas a esta coordenada.
     *
     * @return la lista de rutas asociadas.
     */
    public List<Ruta> getRutas() {
        return rutas;
    }

    /**
     * Establece la lista de rutas asociadas a esta coordenada.
     *
     * @param rutas la lista de rutas a asociar.
     */
    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    /**
     * Compara esta coordenada con otro objeto para determinar si son iguales.
     *
     * @param o el objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordenada that = (Coordenada) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calcula el código hash de la coordenada.
     *
     * @return el código hash de la coordenada.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena de la coordenada.
     *
     * @return una cadena que representa la coordenada.
     */
    @Override
    public String toString() {
        return "Coordenada{" +
                "id=" + id +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", rutas=" + rutas +
                '}';
    }
}