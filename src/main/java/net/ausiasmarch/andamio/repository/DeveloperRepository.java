package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.DeveloperEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DeveloperRepository extends JpaRepository<DeveloperEntity, Long> {

    
      DeveloperEntity findByUsernameAndPassword(String username, String password);

      Page<DeveloperEntity> findByTeamId(Long id_team, Pageable oPageable);
}
