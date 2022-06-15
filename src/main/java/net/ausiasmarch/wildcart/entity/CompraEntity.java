package net.ausiasmarch.wildcart.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "compra")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CompraEntity implements Serializable {

    @Schema(example = "3")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(example = "3")
    private int cantidad;
    @Schema(example = "12.30")
    private double precio;

    @Schema(example = "14/11/2021 09:44", format = "dd/MM/yyyy HH:mm")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime fecha;

    @Schema(example = "0")
    private int descuento_usuario;
    @Schema(example = "0")
    private int descuento_producto;

    @Schema(example = "{\"id\": 1}")
    @ManyToOne
    @JoinColumn(name = "id_factura")
    private FacturaEntity factura;

    @Schema(example = "{\"id\": 1}")
    @ManyToOne
    @JoinColumn(name = "id_producto")
    private ProductoEntity producto;

    public CompraEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public int getDescuento_usuario() {
        return descuento_usuario;
    }

    public void setDescuento_usuario(int descuento_usuario) {
        this.descuento_usuario = descuento_usuario;
    }

    public int getDescuento_producto() {
        return descuento_producto;
    }

    public void setDescuento_producto(int descuento_producto) {
        this.descuento_producto = descuento_producto;
    }

    public FacturaEntity getFactura() {
        return factura;
    }

    public void setFactura(FacturaEntity factura) {
        this.factura = factura;
    }

    public ProductoEntity getProducto() {
        return producto;
    }

    public void setProducto(ProductoEntity producto) {
        this.producto = producto;
    }

}
