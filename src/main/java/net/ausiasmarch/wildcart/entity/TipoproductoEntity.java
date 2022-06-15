package net.ausiasmarch.wildcart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tipoproducto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TipoproductoEntity {

    @Schema(example = "3")    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)    
    private Long id;
    
    @Schema(example = "Productos del tipo AZ1")
    private String nombre;

    @Schema(hidden = true)
    @OneToMany(mappedBy = "tipoproducto")
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
}
