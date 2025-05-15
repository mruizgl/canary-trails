package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.Objects;

/**
 * Representa un comentario asociado a un usuario y una ruta en el sistema.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "comentarios")
public class Comentario {

    /**
     * Identificador único del comentario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Título del comentario.
     */
    private String titulo;

    /**
     * Descripción o contenido del comentario.
     */
    private String descripcion;

    /**
     * Usuario que realizó el comentario.
     * Relación ManyToOne con la entidad Usuario.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    /**
     * Ruta asociada al comentario.
     * Relación ManyToOne con la entidad Ruta.
     */
    @ManyToOne
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    public Comentario() {
    }

    /**
     * Obtiene el identificador del comentario.
     *
     * @return el identificador del comentario.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador del comentario.
     *
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el título del comentario.
     *
     * @return el título del comentario.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del comentario.
     *
     * @param titulo el título a establecer.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene la descripción del comentario.
     *
     * @return la descripción del comentario.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Establece la descripción del comentario.
     *
     * @param descripcion la descripción a establecer.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el usuario que realizó el comentario.
     *
     * @return el usuario asociado al comentario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el usuario que realizó el comentario.
     *
     * @param usuario el usuario a asociar al comentario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la ruta asociada al comentario.
     *
     * @return la ruta asociada al comentario.
     */
    public Ruta getRuta() {
        return ruta;
    }

    /**
     * Establece la ruta asociada al comentario.
     *
     * @param ruta la ruta a asociar al comentario.
     */
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    /**
     * Compara este comentario con otro objeto para determinar si son iguales.
     *
     * @param o el objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Comentario that = (Comentario) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calcula el código hash del comentario.
     *
     * @return el código hash del comentario.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena del comentario.
     *
     * @return una cadena que representa el comentario.
     */
    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", usuario=" + usuario +
                ", ruta=" + ruta +
                '}';
    }
}