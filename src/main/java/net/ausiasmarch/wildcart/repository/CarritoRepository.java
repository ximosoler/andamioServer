package net.ausiasmarch.wildcart.repository;

import java.util.List;
import javax.transaction.Transactional;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    Page<CarritoEntity> findByProductoId(Long id_Producto, Pageable oPageable);

    Page<CarritoEntity> findByUsuarioId(Long id_Usuario, Pageable oPageable);

    List<CarritoEntity> findByUsuarioId(Long id_Usuario);

    long countByUsuarioId(Long id_Usuario);

    Page<CarritoEntity> findAllByUsuario(UsuarioEntity usuario, Pageable oPageable);

    Page<CarritoEntity> findAllByIdIgnoreCaseContainingOrCantidadIgnoreCaseContainingOrPrecioIgnoreCaseContainingOrProductoIgnoreCaseContainingOrUsuarioIgnoreCaseContaining(
            String id, String cantidad, String precio, String producto, String usuario, Pageable oPageable);

    CarritoEntity findByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);

    @Transactional
    int deleteAllByUsuario(UsuarioEntity usuario);

    @Transactional
    int deleteByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);

    @Query(value = "SELECT * FROM carrito WHERE id_usuario = ?1 AND cantidad LIKE  %?2%", nativeQuery = true)
    Page<CarritoEntity> findByUsuarioIdAndCantidad(Long usuarioId, String cantidad, Pageable oPageable);

}
