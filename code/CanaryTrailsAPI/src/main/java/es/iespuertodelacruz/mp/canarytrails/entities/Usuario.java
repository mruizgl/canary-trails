package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
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

    private String nombre;

    private String apellidos;

    @Column(unique = true)
    private String correo;

    private String password;

    private Boolean verificado;

    private String rol;

    @OneToMany(mappedBy = "upload/usuario")
    private List<Ruta> rutasCreadas;

    @OneToMany(mappedBy = "upload/usuario")
    private List<Comentario> comentarios;

    @ManyToMany
    @JoinTable(
            name = "usuario_ruta_favorita",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "ruta_id")
    )
    private List<Ruta> rutasFavoritas;

    @OneToMany(mappedBy = "upload/usuario")
    private List<Fauna> faunas;

    @OneToMany(mappedBy = "upload/usuario")
    private List<Flora> floras;

    public Usuario() {
        this.rutasCreadas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
        this.rutasFavoritas = new ArrayList<>();
        this.faunas = new ArrayList<>();
        this.floras = new ArrayList<>();
    }

    //Crear el usuario al registrar. El validado se establece en 0 por defecto y el rol se establece en el service
    /*public Usuario(String nombre, String apellidos, String correo, String password) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
    }

    public Usuario(Integer id, String nombre, String apellidos, String correo, String password, Boolean verificado,
                   String rol, List<Ruta> rutasCreadas, List<Comentario> comentarios, List<Ruta> rutasFavoritas,
                   List<Fauna> faunas, List<Flora> floras) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
        this.password = password;
        this.verificado = verificado;
        this.rol = rol;
        this.rutasCreadas = rutasCreadas;
        this.comentarios = comentarios;
        this.rutasFavoritas = rutasFavoritas;
        this.faunas = faunas;
        this.floras = floras;
    }*/

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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
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

    public List<Ruta> getRutasCreadas() {
        return rutasCreadas;
    }

    public void setRutasCreadas(List<Ruta> rutasCreadas) {
        this.rutasCreadas = rutasCreadas;
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
                ", apellidos='" + apellidos + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + password + '\'' +
                ", verificado=" + verificado +
                ", rol='" + rol + '\'' +
                ", rutasCreadas=" + rutasCreadas +
                ", comentarios=" + comentarios +
                '}';
    }
}