package net.ausiasmarch.andamio.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import net.ausiasmarch.andamio.entity.ResolutionEntity;

public interface ResolutionRepository extends JpaRepository<ResolutionEntity, Long>{

    Page<ResolutionEntity> findAll(Pageable oPageable);

    Page<ResolutionEntity> findByObservationsIgnoreCaseContaining(String observation, Pageable oPageable);
    
    Page<ResolutionEntity> findByObservationsIgnoreCaseContainingAndIssueId(String observation, Long id_issue, Pageable oPageable);
    
    Page<ResolutionEntity> findByObservationsIgnoreCaseContainingAndDeveloperId(String observation, Long id_developer, Pageable oPageable);
    
    Page<ResolutionEntity> findByIssueId(Long id_issue, Pageable oPageable);
    
    Page<ResolutionEntity> findByIssueIdAndDeveloperId(Long id_issue, Long id_developer, Pageable oPageable);
    
    Page<ResolutionEntity> findByDeveloperId(Long id_developer, Pageable oPageable);
    
    Page<ResolutionEntity> findByObservationsIgnoreCaseContainingAndIssueIdAndDeveloperId(String observations, Long id_issue, Long id_developer, Pageable oPageable);
}
