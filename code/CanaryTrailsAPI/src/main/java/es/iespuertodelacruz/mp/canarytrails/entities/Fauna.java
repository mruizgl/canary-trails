package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Boolean aprobada;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToMany(mappedBy = "faunas")
    private List<Ruta> rutas;

    @Column(columnDefinition = "TEXT")
    private String foto;

    public Fauna() {
        this.rutas = new ArrayList<>();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Fauna fauna = (Fauna) o;
        return Objects.equals(id, fauna.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fauna{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aprobada=" + aprobada +
                ", usuario=" + usuario +
                ", rutas=" + rutas +
                ", foto='" + foto + '\'' +
                '}';
    }
}