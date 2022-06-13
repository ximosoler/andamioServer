package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.CompraEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompraRepository extends JpaRepository<CompraEntity, Long> {

    Page<CompraEntity> findByFacturaId(Long factura, Pageable oPageable);

    Page<CompraEntity> findByProductoId(Long producto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_factura = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%)",
            nativeQuery = true)
    Page<CompraEntity> findByFacturaIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(long IdFactura, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id LIKE  %?1% OR cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%",
            nativeQuery = true)
    Page<CompraEntity> findByIdContainingOrCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(String IdCompra, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_producto = ?1 AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%)",
            nativeQuery = true)
    Page<CompraEntity> findByProductoIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(long IdProducto, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(
            value = "SELECT * FROM compra WHERE id_factura = ?1 AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?2) AND (cantidad LIKE  %?3% OR precio LIKE %?4% OR fecha LIKE %?5% OR descuento_usuario LIKE %?6% OR descuento_producto LIKE %?7%)",
            nativeQuery = true)
    Page<CompraEntity> findByFacturaIdAndUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(long id_factura, long id_usuario, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(value = "SELECT * FROM compra WHERE id_factura = ?1 AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?2)", nativeQuery = true)
    Page<CompraEntity> findByFacturaIdAndUsuarioId(Long id_factura, Long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM compra WHERE id_producto=?1 AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?2)", nativeQuery = true)
    Page<CompraEntity> findByProductoIdAndUsuarioId(long id_producto, long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM compra WHERE id_producto=?1 AND id_factura IN (SELECT id FROM factura WHERE id_usuario = ?2) AND (cantidad LIKE  %?3% OR precio LIKE %?4% OR fecha LIKE %?5% OR descuento_usuario LIKE %?6% OR descuento_producto LIKE %?7%)", nativeQuery = true)
    Page<CompraEntity> findByProductoIdAndUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(long id_producto, long id_usuario, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);

    @Query(value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = ?1)", nativeQuery = true)
    Page<CompraEntity> findByUsuarioId(long id_usuario, Pageable oPageable);

    @Query(value = "SELECT * FROM compra where id_factura IN (SELECT id FROM factura WHERE id_usuario = ?1) AND (cantidad LIKE  %?2% OR precio LIKE %?3% OR fecha LIKE %?4% OR descuento_usuario LIKE %?5% OR descuento_producto LIKE %?6%)", nativeQuery = true)
    Page<CompraEntity> findByUsuarioIdAndCantidadContainingOrPrecioContainingOrFechaContainingOrDescuento_usuarioContainingOrDescuento_productoContaining(long id_usuario, String cantidad, String precio, String fecha, String descuentoUsuario, String descuentoProducto, Pageable oPageable);
}
