package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.TeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamEntity, Long> {

    Page<TeamEntity> findAll(Pageable oPageable);

}
    
    
