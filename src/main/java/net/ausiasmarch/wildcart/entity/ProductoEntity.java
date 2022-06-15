package net.ausiasmarch.wildcart.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "producto")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProductoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Schema(example = "2039981-ASC")
    private String codigo;
    @Schema(example = "Paraguas rojo automático con mango de madera")
    private String nombre;
    @Schema(example = "8")
    private Integer existencias;
    @Schema(example = "22")
    private Double precio;
    @Schema(example = "1")
    private Long imagen;
    @Schema(example = "0")
    private int descuento;

    @Schema(example = "{\"id\": 2}")
    @ManyToOne
    @JoinColumn(name = "id_tipoproducto")
    private TipoproductoEntity tipoproducto;

    @Schema(hidden = true)
    @OneToMany(mappedBy = "producto")
    private final List<CarritoEntity> carritos;

    @Schema(hidden = true)
    @OneToMany(mappedBy = "producto")
    private final List<CompraEntity> compras;

    public ProductoEntity() {
        this.carritos = new ArrayList<>();
        this.compras = new ArrayList<>();
    }

    public ProductoEntity(Long id) {
        this.carritos = new ArrayList<>();
        this.compras = new ArrayList<>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getExistencias() {
        return existencias;
    }

    public void setExistencias(Integer existencias) {
        this.existencias = existencias;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Long getImagen() {
        return imagen;
    }

    public void setImagen(Long imagen) {
        this.imagen = imagen;
    }

    public Integer getDescuento() {
        return descuento;
    }

    public void setDescuento(Integer descuento) {
        this.descuento = descuento;
    }

    public int getCarritos() {
        return this.carritos.size();
    }

    public int getCompras() {
        return compras.size();
    }

    public TipoproductoEntity getTipoproducto() {
        return tipoproducto;
    }

    public void setTipoproducto(TipoproductoEntity tipoproducto) {
        this.tipoproducto = tipoproducto;
    }

}
