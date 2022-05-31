package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.TipoProductoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoproductoRepository extends JpaRepository<TipoProductoEntity, Long> {    

    public Page<TipoProductoEntity> findByNombreIgnoreCaseContaining(String strFilter, Pageable oPageable);

    public Page<TipoProductoEntity> findById(Long longTipoProducto, Pageable oPageable);

    public Page<TipoProductoEntity> findByIdAndNombreIgnoreCaseContaining(Long longTipoProducto, String strFilter, Pageable oPageable);

}
