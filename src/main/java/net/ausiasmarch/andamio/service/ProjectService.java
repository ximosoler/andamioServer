package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.ProjectEntity;
import net.ausiasmarch.andamio.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    private final ProjectRepository oProjectRepository;

    @Autowired
    AuthService oAuthService;

    @Autowired
    public ProjectService(ProjectRepository oProjectRepository) {
        this.oProjectRepository = oProjectRepository;
    }

    public ProjectEntity get(Long id) {
        return oProjectRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Project with id: " + id + " not found"));

    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oProjectRepository.count();
    }
    
}
