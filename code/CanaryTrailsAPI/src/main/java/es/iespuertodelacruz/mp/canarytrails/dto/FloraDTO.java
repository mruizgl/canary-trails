package es.iespuertodelacruz.mp.canarytrails.dto;

import java.util.Objects;

public class FloraDTO {
    private Integer id;
    private String nombre;
    private String especie;
    private String tipoHoja;
    private String salidaFlor;
    private String caidaFlor;
    private String descripcion;
    private boolean aprobada;

    public FloraDTO() {
    }

    public FloraDTO(Integer id, String nombre, String especie, String tipoHoja, String salidaFlor, String caidaFlor,
            String descripcion, boolean aprobada) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.tipoHoja = tipoHoja;
        this.salidaFlor = salidaFlor;
        this.caidaFlor = caidaFlor;
        this.descripcion = descripcion;
        this.aprobada = aprobada;
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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getTipoHoja() {
        return tipoHoja;
    }

    public void setTipoHoja(String tipoHoja) {
        this.tipoHoja = tipoHoja;
    }

    public String getSalidaFlor() {
        return salidaFlor;
    }

    public void setSalidaFlor(String salidaFlor) {
        this.salidaFlor = salidaFlor;
    }

    public String getCaidaFlor() {
        return caidaFlor;
    }

    public void setCaidaFlor(String caidaFlor) {
        this.caidaFlor = caidaFlor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FloraDTO floraDTO = (FloraDTO) o;
        return Objects.equals(id, floraDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "FloraDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", tipoHoja='" + tipoHoja + '\'' +
                ", salidaFlor='" + salidaFlor + '\'' +
                ", caidaFlor='" + caidaFlor + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", aprobada=" + aprobada +
                '}';
    }
}
