package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.ProjectEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    
    Page<ProjectEntity> findAll(Pageable oPageable);

    Page<ProjectEntity> findByTeamId(Long lTeam, Pageable oPageable);

    @Query(value = "SELECT * FROM project WHERE id_team = ?1 AND (project_code LIKE  %?2% OR project_description LIKE %?3% OR url LIKE %?4%)", nativeQuery = true)
    Page<ProjectEntity> findByTeamIdAndProject_codeIgnoreCaseContainingOrProject_descriptionIgnoreCaseContainingOrUrlContaining(Long lTeam, String project_code, String project_description, String url, Pageable oPageable);
    
    @Query(value = "SELECT * FROM project WHERE project_code LIKE  %?1% OR project_description LIKE %?2% OR url LIKE %?3%", nativeQuery = true)
    Page<ProjectEntity> findByProject_codeIgnoreCaseContainingOrProject_descriptionIgnoreCaseContainingOrUrlContaining(String project_code, String project_description, String url, Pageable oPageable);

}