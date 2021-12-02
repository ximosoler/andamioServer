package net.ausiasmarch.wildcart.repository;

import net.ausiasmarch.wildcart.entity.UsuarioEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {

	UsuarioEntity findByLoginAndPassword(String login, String password);

	@Query(value = "select * from usuario where id_tipousuario = ?1 and (nombre like %?2% or dni like %?3% or apellido1 like %?4% or apellido2 like %?5%)", nativeQuery = true)
	Page<UsuarioEntity> findByTipousuarioIdAndNombreIgnoreCaseContainingOrDniIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
			Long filtertype, String nombre, String dni, String apellido1, String apellido2, Pageable oPageable);

	Page<UsuarioEntity> findByNombreIgnoreCaseContainingOrDniIgnoreCaseContainingOrApellido1IgnoreCaseContainingOrApellido2IgnoreCaseContaining(
			String nombre, String dni, String apellido1, String apellido2, Pageable oPageable);
}
