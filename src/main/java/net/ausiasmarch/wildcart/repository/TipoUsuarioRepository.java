package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.TipoUsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TipoUsuarioRepository extends JpaRepository<TipoUsuarioEntity, Long> {

}
