package net.ausiasmarch.wildcart.repository;


import javax.transaction.Transactional;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import net.ausiasmarch.wildcart.entity.ProductoEntity;
import net.ausiasmarch.wildcart.entity.UsuarioEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    Page<CarritoEntity> findAllByUsuario(UsuarioEntity usuario, Pageable oPageable);
    Page<CarritoEntity> findAllByIdIgnoreCaseContainingOrCantidadIgnoreCaseContainingOrPrecioIgnoreCaseContainingOrProductoIgnoreCaseContainingOrUsuarioIgnoreCaseContaining(String id, String cantidad, String precio, String producto, String usuario, Pageable oPageable);

    CarritoEntity findByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);
    @Transactional
    int deleteAllByUsuario(UsuarioEntity usuario);
    @Transactional
    int deleteByUsuarioAndProducto(UsuarioEntity usuario, ProductoEntity producto);
}
