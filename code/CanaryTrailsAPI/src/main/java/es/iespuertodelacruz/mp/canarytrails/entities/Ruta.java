package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Ruta en el sistema.
 * Esta clase está mapeada a la tabla "rutas" en la base de datos.
 */
@Entity
@Table(name = "rutas")
public class Ruta {

    /**
     * Identificador único de la ruta.
     * Generado automáticamente con el autoincrementsl.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de la ruta.
     */
    private String nombre;

    /**
     * Dificultad de la ruta.
     */
    private String dificultad;

    /**
     * Tiempo estimado de duración de la ruta en minutos.
     * Mapeado a la columna "tiempo_duracion" en la base de datos.
     */
    @Column(name = "tiempo_duracion")
    private Long tiempoDuracion;

    /**
     * Distancia de la ruta en metros.
     * Mapeado a la columna "distancia_metros" en la base de datos.
     */
    @Column(name = "distancia_metros")
    private Float distanciaMetros;

    /**
     * Desnivel acumulado de la ruta en metros.
     */
    private Float desnivel;

    /**
     * Indica si la ruta está aprobada.
     */
    private Boolean aprobada;

    /**
     * Usuario que creó la ruta.
     * Relación de muchos a uno con la entidad Usuario.
     * Mapeado a la columna "usuario_id" en la base de datos.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Relaciones

    /**
     * Lista de comentarios asociados a la ruta.
     * Relación de uno a muchos mapeada por el atributo "ruta" en la entidad Comentario.
     */
    @OneToMany(mappedBy = "ruta")
    private List<Comentario> comentarios;

    /**
     * Lista de municipios asociados a la ruta.
     * Relación de muchos a muchos con la entidad Municipio.
     * Mapeada a la tabla intermedia "ruta_municipio".
     */
    @ManyToMany
    @JoinTable(
            name = "ruta_municipio",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "municipio_id")
    )
    private List<Municipio> municipios;

    /**
     * Lista de floras asociadas a la ruta.
     * Relación de muchos a muchos con la entidad Flora.
     * Mapeada a la tabla intermedia "ruta_flora".
     */
    @ManyToMany
    @JoinTable(
            name = "ruta_flora",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "flora_id")
    )
    private List<Flora> floras;

    /**
     * Lista de faunas asociadas a la ruta.
     * Relación de muchos a muchos con la entidad Fauna.
     * Mapeada a la tabla intermedia "ruta_fauna".
     */
    @ManyToMany
    @JoinTable(
            name = "ruta_fauna",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "fauna_id")
    )
    private List<Fauna> faunas;

    /**
     * Lista de coordenadas asociadas a la ruta.
     * Relación de muchos a muchos con la entidad Coordenada.
     * Mapeada a la tabla intermedia "coordenada_ruta".
     */
    @ManyToMany
    @JoinTable(
            name = "coordenada_ruta",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "coordenada_id")
    )
    private List<Coordenada> coordenadas;

    /**
     * Lista de usuarios que tienen esta ruta como favorita.
     * Relación de muchos a muchos mapeada por el atributo "rutasFavoritas" en la entidad Usuario.
     */
    @ManyToMany(mappedBy = "rutasFavoritas")
    private List<Usuario> usuariosQueLaTienenComoFavorita;

    /**
     * Obtiene el identificador único de la ruta.
     * @return el identificador de la ruta.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único de la ruta.
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de la ruta.
     * @return el nombre de la ruta.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de la ruta.
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la dificultad de la ruta.
     * @return la dificultad de la ruta.
     */
    public String getDificultad() {
        return dificultad;
    }

    /**
     * Establece la dificultad de la ruta.
     * @param dificultad la dificultad a establecer.
     */
    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    /**
     * Obtiene el tiempo estimado de duración de la ruta.
     * @return el tiempo de duración en minutos.
     */
    public Long getTiempoDuracion() {
        return tiempoDuracion;
    }

    /**
     * Establece el tiempo estimado de duración de la ruta.
     * @param tiempoDuracion el tiempo de duración a establecer.
     */
    public void setTiempoDuracion(Long tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    /**
     * Obtiene la distancia de la ruta en metros.
     * @return la distancia en metros.
     */
    public Float getDistanciaMetros() {
        return distanciaMetros;
    }

    /**
     * Establece la distancia de la ruta en metros.
     * @param distanciaMetros la distancia a establecer.
     */
    public void setDistanciaMetros(Float distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }

    /**
     * Obtiene el desnivel acumulado de la ruta.
     * @return el desnivel en metros.
     */
    public Float getDesnivel() {
        return desnivel;
    }

    /**
     * Establece el desnivel acumulado de la ruta.
     * @param desnivel el desnivel a establecer.
     */
    public void setDesnivel(Float desnivel) {
        this.desnivel = desnivel;
    }

    /**
     * Obtiene el estado de aprobación de la ruta.
     * @return true si está aprobada, false en caso contrario.
     */
    public Boolean getAprobada() {
        return aprobada;
    }

    /**
     * Establece el estado de aprobación de la ruta.
     * @param aprobada el estado de aprobación a establecer.
     */
    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    /**
     * Obtiene el usuario que creó la ruta.
     * @return el usuario creador de la ruta.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que creó la ruta.
     * @param usuario el usuario a establecer.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la lista de comentarios asociados a la ruta.
     * @return la lista de comentarios.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Establece la lista de comentarios asociados a la ruta.
     * @param comentarios la lista de comentarios a establecer.
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Obtiene la lista de municipios asociados a la ruta.
     * @return la lista de municipios.
     */
    public List<Municipio> getMunicipios() {
        return municipios;
    }

    /**
     * Establece la lista de municipios asociados a la ruta.
     * @param municipios la lista de municipios a establecer.
     */
    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    /**
     * Obtiene la lista de floras asociadas a la ruta.
     * @return la lista de floras.
     */
    public List<Flora> getFloras() {
        return floras;
    }

    /**
     * Establece la lista de floras asociadas a la ruta.
     * @param floras la lista de floras a establecer.
     */
    public void setFloras(List<Flora> floras) {
        this.floras = floras;
    }

    /**
     * Obtiene la lista de faunas asociadas a la ruta.
     * @return la lista de faunas.
     */
    public List<Fauna> getFaunas() {
        return faunas;
    }

    /**
     * Establece la lista de faunas asociadas a la ruta.
     * @param faunas la lista de faunas a establecer.
     */
    public void setFaunas(List<Fauna> faunas) {
        this.faunas = faunas;
    }

    /**
     * Obtiene la lista de coordenadas asociadas a la ruta.
     * @return la lista de coordenadas.
     */
    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    /**
     * Establece la lista de coordenadas asociadas a la ruta.
     * @param coordenadas la lista de coordenadas a establecer.
     */
    public void setCoordenadas(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }

    /**
     * Obtiene la lista de usuarios que tienen esta ruta como favorita.
     * @return la lista de usuarios.
     */
    public List<Usuario> getUsuariosQueLaTienenComoFavorita() {
        return usuariosQueLaTienenComoFavorita;
    }

    /**
     * Establece la lista de usuarios que tienen esta ruta como favorita.
     * @param usuariosQueLaTienenComoFavorita la lista de usuarios a establecer.
     */
    public void setUsuariosQueLaTienenComoFavorita(List<Usuario> usuariosQueLaTienenComoFavorita) {
        this.usuariosQueLaTienenComoFavorita = usuariosQueLaTienenComoFavorita;
    }

    /**
     * Compara esta ruta con otro objeto para determinar si son iguales.
     * Dos rutas son iguales si tienen el mismo identificador.
     * @param o el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ruta ruta = (Ruta) o;
        return Objects.equals(id, ruta.id);
    }

    /**
     * Calcula el código hash de la ruta basado en su identificador.
     * @return el código hash de la ruta.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena de la ruta.
     * @return una cadena que representa la ruta, incluyendo sus atributos.
     */
    @Override
    public String toString() {
        return "Ruta{" +
                "id=" + id +
                ", nombre=" + nombre +
                ", dificultad=" + dificultad +
                ", tiempoDuracion=" + tiempoDuracion +
                ", distanciaMetros=" + distanciaMetros +
                ", desnivel=" + desnivel +
                ", aprobada=" + aprobada +
                ", usuario=" + usuario +
                ", comentarios=" + comentarios +
                ", municipios=" + municipios +
                ", floras=" + floras +
                ", faunas=" + faunas +
                ", coordenadas=" + coordenadas +
                '}';
    }
}