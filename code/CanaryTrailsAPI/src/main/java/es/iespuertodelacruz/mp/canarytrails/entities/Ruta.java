package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Ruta en el sistema.
 * Esta clase está mapeada a la tabla "rutas" en la base de datos.
 */
@Entity
@Table(name = "rutas")
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    private String dificultad;

    @Column(name = "tiempo_duracion")
    private Long tiempoDuracion;

    @Column(name = "distancia_metros")
    private Float distanciaMetros;

    private Float desnivel;

    private Boolean aprobada;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @OneToMany(mappedBy = "ruta")
    private List<Comentario> comentarios;

    @ElementCollection
    @CollectionTable(name = "ruta_foto", joinColumns = @JoinColumn(name = "ruta_id"))
    @Column(name = "nombre_foto")
    private List<String> fotos;

    @ManyToMany
    @JoinTable(
            name = "ruta_municipio",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "municipio_id")
    )
    private List<Municipio> municipios;

    @ManyToMany
    @JoinTable(
            name = "ruta_flora",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "flora_id")
    )
    private List<Flora> floras;

    @ManyToMany
    @JoinTable(
            name = "ruta_fauna",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "fauna_id")
    )
    private List<Fauna> faunas;

    @ManyToMany
    @JoinTable(
            name = "coordenada_ruta",
            joinColumns = @JoinColumn(name = "ruta_id"),
            inverseJoinColumns = @JoinColumn(name = "coordenada_id")
    )
    private List<Coordenada> coordenadas;

    @ManyToMany(mappedBy = "rutasFavoritas")
    private List<Usuario> usuariosQueLaTienenComoFavorita;

    public Ruta() {
        this.comentarios = new ArrayList<>();
        this.municipios = new ArrayList<>();
        this.floras = new ArrayList<>();
        this.faunas = new ArrayList<>();
        this.coordenadas = new ArrayList<>();
        this.fotos = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public Long getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(Long tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public Float getDistanciaMetros() {
        return distanciaMetros;
    }

    public void setDistanciaMetros(Float distanciaMetros) {
        this.distanciaMetros = distanciaMetros;
    }

    public Float getDesnivel() {
        return desnivel;
    }

    public void setDesnivel(Float desnivel) {
        this.desnivel = desnivel;
    }

    public Boolean getAprobada() {
        return aprobada;
    }

    public void setAprobada(Boolean aprobada) {
        this.aprobada = aprobada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public List<Flora> getFloras() {
        return floras;
    }

    public void setFloras(List<Flora> floras) {
        this.floras = floras;
    }

    public List<Fauna> getFaunas() {
        return faunas;
    }

    public void setFaunas(List<Fauna> faunas) {
        this.faunas = faunas;
    }

    public List<Coordenada> getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(List<Coordenada> coordenadas) {
        this.coordenadas = coordenadas;
    }

    public List<Usuario> getUsuariosQueLaTienenComoFavorita() {
        return usuariosQueLaTienenComoFavorita;
    }

    public void setUsuariosQueLaTienenComoFavorita(List<Usuario> usuariosQueLaTienenComoFavorita) {
        this.usuariosQueLaTienenComoFavorita = usuariosQueLaTienenComoFavorita;
    }

    public List<String> getFotos() {
        return fotos;
    }

    public void setFotos(List<String> fotos) {
        this.fotos = fotos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ruta ruta = (Ruta) o;
        return Objects.equals(id, ruta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Ruta{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", dificultad='" + dificultad + '\'' +
                ", tiempoDuracion=" + tiempoDuracion +
                ", distanciaMetros=" + distanciaMetros +
                ", desnivel=" + desnivel +
                ", aprobada=" + aprobada +
                ", usuario=" + usuario +
                ", comentarios=" + comentarios +
                ", fotos=" + fotos +
                ", municipios=" + municipios +
                ", floras=" + floras +
                ", faunas=" + faunas +
                ", coordenadas=" + coordenadas +
                ", usuariosQueLaTienenComoFavorita=" + usuariosQueLaTienenComoFavorita +
                '}';
    }
}