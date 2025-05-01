package es.iespuertodelacruz.mp.canarytrails.dto;

import java.util.Objects;

public class ZonaDTO {
    private Integer id;
    private String nombre;

    public ZonaDTO() {
    }

    public ZonaDTO(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ZonaDTO zonaDTO = (ZonaDTO) o;
        return Objects.equals(id, zonaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}