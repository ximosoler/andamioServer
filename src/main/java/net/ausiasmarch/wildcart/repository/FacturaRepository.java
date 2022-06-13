package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.FacturaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {

    @Query(value = "SELECT * FROM factura WHERE id_usuario = ?1", nativeQuery = true)
    Page<FacturaEntity> findByUsuarioId(Long id_usuario, Pageable pageable);

    @Query(value = "SELECT * FROM factura WHERE (iva LIKE  %?2% OR fecha LIKE %?3%)", nativeQuery = true)
    Page<FacturaEntity> findByIvaContainingOrFechaContaining(String iva, String fecha, Pageable oPageable);

    @Query(value = "SELECT * FROM factura WHERE id_usuario = ?1 AND (iva LIKE  %?2% OR fecha LIKE %?3%)", nativeQuery = true)
    Page<FacturaEntity> findByUsuarioIdAndIvaContainingOrFechaContaining(long id_usuario, String iva, String fecha, Pageable oPageable);

}
