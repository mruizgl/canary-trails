package es.iespuertodelacruz.mp.canarytrails.entities;

import jakarta.persistence.*;
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

    /**
     * Identificador único del usuario.
     * Generado automáticamente con el autoincremental.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Apellidos del usuario.
     */
    private String apellidos;

    /**
     * Correo electrónico del usuario.
     * Debe ser único en la base de datos.
     */
    @Column(unique = true)
    private String correo;

    /**
     * Contraseña del usuario.
     */
    private String password;

    /**
     * Indica si el usuario ha verificado su cuenta.
     */
    private Boolean verificado;

    /**
     * Rol del usuario en el sistema.
     */
    private String rol;

    /**
     * Lista de rutas creadas por el usuario.
     * Relación de uno a muchos mapeada por el atributo "usuario" en la entidad Ruta.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Ruta> rutasCreadas;

    /**
     * Lista de comentarios realizados por el usuario.
     * Relación de uno a muchos mapeada por el atributo "usuario" en la entidad Comentario.
     */
    @OneToMany(mappedBy = "usuario")
    private List<Comentario> comentarios;

    /**
     * Lista de rutas favoritas del usuario.
     * Relación de muchos a muchos con la entidad Ruta.
     * Mapeada a la tabla intermedia "usuario_ruta_favorita".
     */
    @ManyToMany
    @JoinTable(
            name = "usuario_ruta_favorita",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "ruta_id")
    )
    private List<Ruta> rutasFavoritas;

    /**
     * Lista de faunas asociadas al usuario.
     * Relación de muchos a muchos con la entidad Fauna.
     * Mapeada a la tabla intermedia "usuario_fauna".
     */
    @ManyToMany
    @JoinTable(
            name = "usuario_fauna",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "fauna_id")
    )
    private List<Fauna> faunas;

    /**
     * Lista de floras asociadas al usuario.
     * Relación de muchos a muchos con la entidad Flora.
     * Mapeada a la tabla intermedia "usuario_flora".
     */
    @ManyToMany
    @JoinTable(
            name = "usuario_flora",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "flora_id")
    )
    private List<Flora> floras;

    public Usuario() {
    }

    //Crear el usuario al registrar. El validado se establece en 0 por defecto y el rol se establece en el service
    public Usuario(String nombre, String apellidos, String correo, String password) {
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
    }

    /**
     * Obtiene el identificador único del usuario.
     * @return el identificador del usuario.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Establece el identificador único del usuario.
     * @param id el identificador a establecer.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del usuario.
     * @return el nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del usuario.
     * @param nombre el nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del usuario.
     * @return los apellidos del usuario.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del usuario.
     * @param apellidos los apellidos a establecer.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     * @return el correo del usuario.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * Establece el correo electrónico del usuario.
     * @param correo el correo a establecer.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * Obtiene la contraseña del usuario.
     * @return la contraseña del usuario.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece la contraseña del usuario.
     * @param contraseña la contraseña a establecer.
     */
    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    /**
     * Obtiene el estado de verificación del usuario.
     * @return true si está verificado, false en caso contrario.
     */
    public Boolean getVerificado() {
        return verificado;
    }

    /**
     * Establece el estado de verificación del usuario.
     * @param verificado el estado de verificación a establecer.
     */
    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    /**
     * Obtiene el rol del usuario.
     * @return el rol del usuario.
     */
    public String getRol() {
        return rol;
    }

    /**
     * Establece el rol del usuario.
     * @param rol el rol a establecer.
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Obtiene la lista de rutas creadas por el usuario.
     * @return la lista de rutas creadas.
     */
    public List<Ruta> getRutasCreadas() {
        return rutasCreadas;
    }

    /**
     * Establece la lista de rutas creadas por el usuario.
     * @param rutasCreadas la lista de rutas a establecer.
     */
    public void setRutasCreadas(List<Ruta> rutasCreadas) {
        this.rutasCreadas = rutasCreadas;
    }

    /**
     * Obtiene la lista de comentarios realizados por el usuario.
     * @return la lista de comentarios.
     */
    public List<Comentario> getComentarios() {
        return comentarios;
    }

    /**
     * Establece la lista de comentarios realizados por el usuario.
     * @param comentarios la lista de comentarios a establecer.
     */
    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    /**
     * Obtiene la lista de rutas favoritas del usuario.
     * @return la lista de rutas favoritas.
     */
    public List<Ruta> getRutasFavoritas() {
        return rutasFavoritas;
    }

    /**
     * Establece la lista de rutas favoritas del usuario.
     * @param rutasFavoritas la lista de rutas a establecer.
     */
    public void setRutasFavoritas(List<Ruta> rutasFavoritas) {
        this.rutasFavoritas = rutasFavoritas;
    }

    /**
     * Obtiene la lista de faunas asociadas al usuario.
     * @return la lista de faunas.
     */
    public List<Fauna> getFaunas() {
        return faunas;
    }

    /**
     * Establece la lista de faunas asociadas al usuario.
     * @param faunas la lista de faunas a establecer.
     */
    public void setFaunas(List<Fauna> faunas) {
        this.faunas = faunas;
    }

    /**
     * Obtiene la lista de floras asociadas al usuario.
     * @return la lista de floras.
     */
    public List<Flora> getFloras() {
        return floras;
    }

    /**
     * Establece la lista de floras asociadas al usuario.
     * @param floras la lista de floras a establecer.
     */
    public void setFloras(List<Flora> floras) {
        this.floras = floras;
    }

    /**
     * Compara este usuario con otro objeto para determinar si son iguales.
     * Dos usuarios son iguales si tienen el mismo identificador.
     * @param o el objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    /**
     * Calcula el código hash del usuario basado en su identificador.
     * @return el código hash del usuario.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Devuelve una representación en forma de cadena del usuario.
     * @return una cadena que representa el usuario, incluyendo sus atributos.
     */
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