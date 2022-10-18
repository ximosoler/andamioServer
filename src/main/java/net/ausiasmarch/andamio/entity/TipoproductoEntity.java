package net.ausiasmarch.andamio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "tipoproducto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoproductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "tipoproducto", fetch = FetchType.LAZY)
    private final List<ProductoEntity> productos;

    public TipoproductoEntity() {
        this.productos = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getProductos() {
        return productos.size();
    }

    @PreRemove
    public void nullify() {
        this.productos.forEach(c -> c.setTipoproducto(null));
    }
}
