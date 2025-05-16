package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Municipio en el sistema.
 * Esta clase está mapeada a la tabla "municipios" en la base de datos.
 * Proporciona información sobre un municipio, incluyendo su nombre, altitud media,
 * coordenadas geográficas, zona asociada y las rutas relacionadas.
 *
 * @author Melissa R.G.
 * @author Pedro M.E.
 */
@Entity
@Table(name = "municipios")
public class Municipio {

    /**
     * Identificador único del municipio.
     * Generado automáticamente con el autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del municipio.
     */
    private String nombre;

    /**
     * Altitud media del municipio en metros.
     * Mapeado a la columna "altitud_media" en la base de datos.
     */
    @Column(name = "altitud_media")
    private Integer altitudMedia;

    /**
     * Latitud geográfica del municipio.
     * Mapeado a la columna "latitud_geografica" con precisión de 10 y escala de 6.
     */
    @Column(name = "latitud_geografica", precision = 10, scale = 6)
    private BigDecimal latitudGeografica;

    /**
     * Longitud geográfica del municipio.
     * Mapeado a la columna "longitud_geografica" con precisión de 10 y escala de 6.
     */
    @Column(name = "longitud_geografica", precision = 10, scale = 6)
    private BigDecimal longitudGeografica;

    /**
     * Zona a la que pertenece el municipio.
     * Relación de muchos a uno con la entidad Zona.
     * Mapeado a la columna "zona_id" en la base de datos.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;

    /**
     * Lista de rutas asociadas al municipio.
     * Relación de muchos a muchos mapeada por el atributo "municipios" en la entidad Ruta.
     */
    @ManyToMany(mappedBy = "municipios")
    private List<Ruta> rutas;

    public Municipio() {
        this.rutas = new ArrayList<>();
    }

    /**
     * Obtiene el identificador único del municipio.
     *
     * @return el identificador del municipio.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único del municipio.
     *
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del municipio.
     *
     * @return el nombre del municipio.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del municipio.
     *
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la altitud media del municipio en metros.
     *
     * @return la altitud media del municipio.
     */
    public Integer getAltitudMedia() {
        return altitudMedia;
    }

    /**
     * Establece la altitud media del municipio en metros.
     *
     * @param altitudMedia la altitud media a establecer.
     */
    public void setAltitudMedia(Integer altitudMedia) {
        this.altitudMedia = altitudMedia;
    }

    /**
     * Obtiene la latitud geográfica del municipio.
     *
     * @return la latitud geográfica del municipio.
     */
    public BigDecimal getLatitudGeografica() {
        return latitudGeografica;
    }

    /**
     * Establece la latitud geográfica del municipio.
     *
     * @param latitudGeografica la latitud geográfica a establecer.
     */
    public void setLatitudGeografica(BigDecimal latitudGeografica) {
        this.latitudGeografica = latitudGeografica;
    }

    /**
     * Obtiene la longitud geográfica del municipio.
     *
     * @return la longitud geográfica del municipio.
     */
    public BigDecimal getLongitudGeografica() {
        return longitudGeografica;
    }

    /**
     * Establece la longitud geográfica del municipio.
     *
     * @param longitudGeografica la longitud geográfica a establecer.
     */
    public void setLongitudGeografica(BigDecimal longitudGeografica) {
        this.longitudGeografica = longitudGeografica;
    }

    /**
     * Obtiene la zona a la que pertenece el municipio.
     *
     * @return la zona asociada al municipio.
     */
    public Zona getZona() {
        return zona;
    }

    /**
     * Establece la zona a la que pertenece el municipio.
     *
     * @param zona la zona a establecer.
     */
    public void setZona(Zona zona) {
        this.zona = zona;
    }

    /**
     * Obtiene la lista de rutas asociadas al municipio.
     *
     * @return la lista de rutas asociadas.
     */
    public List<Ruta> getRutas() {
        return rutas;
    }

    /**
     * Establece la lista de rutas asociadas al municipio.
     *
     * @param rutas la lista de rutas a establecer.
     */
    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    /**
     * Compara este municipio con otro objeto para determinar si son iguales.
     * Dos municipios son iguales si tienen el mismo identificador.
     *
     * @param o el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Municipio municipio = (Municipio) o;
        return Objects.equals(id, municipio.id);
    }

    /**
     * Calcula el código hash del municipio basado en su identificador.
     *
     * @return el código hash del municipio.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena del municipio.
     *
     * @return una cadena que representa el municipio, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "Municipio{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", altitudMedia=" + altitudMedia +
                ", latitudGeografica=" + latitudGeografica +
                ", longitudGeografica=" + longitudGeografica +
                ", zona=" + zona +
                ", rutas=" + rutas +
                '}';
    }
}