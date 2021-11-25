package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.ProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<ProductoEntity, Long> {

    Page<ProductoEntity> findByNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(String nombre, String codigo, Pageable oPageable);

    public Page<ProductoEntity> findByTipoproductoIdAndNombreIgnoreCaseContainingOrCodigoIgnoreCaseContaining(Long filtertype, String filter, String filter0, Pageable oPageable);
}
