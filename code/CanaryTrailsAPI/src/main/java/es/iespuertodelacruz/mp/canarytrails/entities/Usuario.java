package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Representa una entidad Usuario en el sistema.
 * Esta clase está mapeada a la tabla "usuarios" en la base de datos.
 * @author Melissa R.G. y Pedro M.E.
 */
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String nombre;

    @Column(unique = true)
    private String correo;

    private String password;

    @Column(name="token_verificacion")
    private String tokenVerificacion;

    @Column(name="fecha_creacion")
    @Convert(converter= DateToLongConverter.class)
    private Date fechaCreacion;

    private Boolean verificado;

    private String rol;

    @OneToMany(mappedBy = "usuario")
    private List<Ruta> rutas;

    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    @ManyToMany
    @JoinTable(
            name = "usuario_ruta_favorita",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "ruta_id")
    )
    private List<Ruta> rutasFavoritas;

    @OneToMany(mappedBy = "usuario")
    private List<Fauna> faunas;

    @OneToMany(mappedBy = "usuario")
    private List<Flora> floras;

    @Column(columnDefinition = "TEXT")
    private String foto;

    public Usuario() {
        this.rutas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.rutasFavoritas = new ArrayList<>();
        this.faunas = new ArrayList<>();
        this.floras = new ArrayList<>();
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

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public List<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(List<Ruta> rutas) {
        this.rutas = rutas;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Ruta> getRutasFavoritas() {
        return rutasFavoritas;
    }

    public void setRutasFavoritas(List<Ruta> rutasFavoritas) {
        this.rutasFavoritas = rutasFavoritas;
    }

    public List<Fauna> getFaunas() {
        return faunas;
    }

    public void setFaunas(List<Fauna> faunas) {
        this.faunas = faunas;
    }

    public List<Flora> getFloras() {
        return floras;
    }

    public void setFloras(List<Flora> floras) {
        this.floras = floras;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getTokenVerificacion() {
        return tokenVerificacion;
    }

    public void setTokenVerificacion(String tokenVerificacion) {
        this.tokenVerificacion = tokenVerificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", tokenVerificacion='" + tokenVerificacion + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", verificado=" + verificado +
                ", rol='" + rol + '\'' +
                ", rutas=" + rutas +
                ", comentarios=" + comentarios +
                ", rutasFavoritas=" + rutasFavoritas +
                ", faunas=" + faunas +
                ", floras=" + floras +
                ", foto='" + foto + '\'' +
                '}';
    }
}