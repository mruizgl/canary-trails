package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Flora en el sistema.
 * Esta clase está mapeada a la tabla "floras" en la base de datos.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "floras")
public class Flora {

    /**
     * Identificador único de la flora.
     * Generado automáticamente con el autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la flora.
     */
    private String nombre;

    /**
     * Especie de la flora.
     */
    private String especie;

    /**
     * Tipo de hoja de la flora.
     * Mapeado a la columna "tipo_hoja" en la base de datos.
     */
    @Column(name = "tipo_hoja")
    private String tipoHoja;

    /**
     * Periodo de salida de la flor.
     * Mapeado a la columna "salida_flor" en la base de datos.
     */
    @Column(name = "salida_flor")
    private String salidaFlor;

    /**
     * Periodo de caída de la flor.
     * Mapeado a la columna "caida_flor" en la base de datos.
     */
    @Column(name = "caida_flor")
    private String caidaFlor;

    /**
     * Descripción detallada de la flora.
     * Almacenada como texto en la base de datos.
     */
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    /**
     * Indica si la flora está aprobada.
     */
    private Boolean aprobada;

    /**
     * Lista de usuarios asociados a esta flora.
     * Relación de muchos a muchos mapeada por el atributo "floras" en la entidad Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    /**
     * Lista de rutas asociadas a esta flora.
     * Relación de muchos a muchos mapeada por el atributo "floras" en la entidad Ruta.
     */
    @ManyToMany(mappedBy = "floras")
    private List<Ruta> rutas;

    @Column(columnDefinition = "TEXT")
    private String foto;

    public Flora() {
        this.rutas = new ArrayList<>();
    }

    /**
     * Obtiene el identificador único de la flora.
     * @return el identificador de la flora.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único de la flora.
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la flora.
     * @return el nombre de la flora.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la flora.
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la especie de la flora.
     * @return la especie de la flora.
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Establece la especie de la flora.
     * @param especie la especie a establecer.
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    /**
     * Obtiene el tipo de hoja de la flora.
     * @return el tipo de hoja de la flora.
     */
    public String getTipoHoja() {
        return tipoHoja;
    }

    /**
     * Establece el tipo de hoja de la flora.
     * @param tipoHoja el tipo de hoja a establecer.
     */
    public void setTipoHoja(String tipoHoja) {
        this.tipoHoja = tipoHoja;
    }

    /**
     * Obtiene el periodo de salida de la flor.
     * @return el periodo de salida de la flor.
     */
    public String getSalidaFlor() {
        return salidaFlor;
    }

    /**
     * Establece el periodo de salida de la flor.
     * @param salidaFlor el periodo de salida a establecer.
     */
    public void setSalidaFlor(String salidaFlor) {
        this.salidaFlor = salidaFlor;
    }

    /**
     * Obtiene el periodo de caída de la flor.
     * @return el periodo de caída de la flor.
     */
    public String getCaidaFlor() {
        return caidaFlor;
    }

    /**
     * Establece el periodo de caída de la flor.
     * @param caidaFlor el periodo de caída a establecer.
     */
    public void setCaidaFlor(String caidaFlor) {
        this.caidaFlor = caidaFlor;
    }

    /**
     * Obtiene la descripción de la flora.
     * @return la descripción de la flora.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción de la flora.
     * @param descripcion la descripción a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado de aprobación de la flora.
     * @return true si está aprobada, false en caso contrario.
     */
    public Boolean getAprobada() {
        return aprobada;
    }

    /**
     * Establece el estado de aprobación de la flora.
     * @param aprobada el estado de aprobación a establecer.
     */
    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de rutas asociadas a esta flora.
     * @return la lista de rutas.
     */
    public List<Ruta> getRutas() {
        return rutas;
    }

    /**
     * Establece la lista de rutas asociadas a esta flora.
     * @param rutas la lista de rutas a establecer.
     */
    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Compara esta flora con otro objeto para determinar si son iguales.
     * Dos instancias de Flora se consideran iguales si tienen el mismo identificador.
     *
     * @param o el objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Flora flora = (Flora) o;
        return Objects.equals(id, flora.id);
    }

    /**
     * Calcula el código hash de la flora basado en su identificador.
     *
     * @return el código hash de la flora.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Flora{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", tipoHoja='" + tipoHoja + '\'' +
                ", salidaFlor='" + salidaFlor + '\'' +
                ", caidaFlor='" + caidaFlor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aprobada=" + aprobada +
                ", usuario=" + usuario +
                ", rutas=" + rutas +
                ", foto='" + foto + '\'' +
                '}';
    }
}