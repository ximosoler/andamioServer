package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

    UsuarioEntity findByLoginAndPassword(String login, String password);

    Page<UsuarioEntity> findByTipousuarioIdAndNombreIgnoreCaseContaining(Long tipo, String nombre, Pageable oPageable);

    Page<UsuarioEntity> findByNombreIgnoreCaseContaining(String nombre, Pageable oPageable);
}
