package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.FacturaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FacturaRepository extends JpaRepository<FacturaEntity, Long> {

 @Query(value = "SELECT * FROM factura f WHERE f.id_usuario = :id_usuario", nativeQuery = true)
    Page<FacturaEntity> findByFacturaXUsuario(Long id_usuario, Pageable pageable);
    
@Query(value = "SELECT COUNT(*) FROM factura where id_usuario = :id_usuario ", nativeQuery = true)
    Long findByFacturaIdUsuarioCount(Long id_usuario);


}