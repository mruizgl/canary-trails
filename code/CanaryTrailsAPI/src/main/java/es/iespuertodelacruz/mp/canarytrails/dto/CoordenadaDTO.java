package es.iespuertodelacruz.mp.canarytrails.dto;

import java.math.BigDecimal;
import java.util.Objects;

public class CoordenadaDTO {
    private Integer id;
    private BigDecimal latitud;
    private BigDecimal longitud;

    public CoordenadaDTO() {
    }

    public CoordenadaDTO(Integer id, BigDecimal latitud, BigDecimal longitud) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getLatitud() {
        return latitud;
    }

    public void setLatitud(BigDecimal latitud) {
        this.latitud = latitud;
    }

    public BigDecimal getLongitud() {
        return longitud;
    }

    public void setLongitud(BigDecimal longitud) {
        this.longitud = longitud;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CoordenadaDTO that = (CoordenadaDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CoordenadaDTO{" +
                "id=" + id +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                '}';
    }
}