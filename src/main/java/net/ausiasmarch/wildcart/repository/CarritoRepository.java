package net.ausiasmarch.wildcart.repository;

import java.util.List;
import net.ausiasmarch.wildcart.entity.CarritoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoRepository extends JpaRepository<CarritoEntity, Long> {

    Page<CarritoEntity> findByProductoId(Long id_Producto, Pageable oPageable);

    Page<CarritoEntity> findByUsuarioId(Long id_Usuario, Pageable oPageable);

    List<CarritoEntity> findByUsuarioId(Long id_usuario);

    List<CarritoEntity> findByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);

    long countByUsuarioId(Long id_Usuario);

    Long countByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);

    long deleteByUsuarioIdAndProductoId(Long id_usuario, Long id_producto);

    long deleteByUsuarioId(Long id_usuario);

}
