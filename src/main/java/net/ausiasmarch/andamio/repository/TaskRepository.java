package net.ausiasmarch.andamio.repository;

import net.ausiasmarch.andamio.entity.TaskEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface TaskRepository extends JpaRepository<TaskEntity, Long> {
    
    Page<TaskEntity> findAll(Pageable oPageable);

    Page<TaskEntity> findByProjectIdAndComplexityContainingIgnoreCase(long lProject, String strFilter, Pageable oPageable);

    Page<TaskEntity> findByDescriptionContainingIgnoreCase(String strFilter, Pageable oPageable);

    Page<TaskEntity> findByProjectId(long lProject, Pageable oPageable);


    
}