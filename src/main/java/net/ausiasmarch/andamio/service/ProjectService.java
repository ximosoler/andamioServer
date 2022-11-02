package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.ProjectEntity;
import net.ausiasmarch.andamio.exception.CannotPerformOperationException;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.helper.RandomHelper;
import net.ausiasmarch.andamio.repository.ProjectRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public void validate(Long id) {
        if (!oProjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public ProjectEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oProjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project with id: " + id + " not found"));

    }

    public Page<ProjectEntity> getPage(Pageable oPageable, String strFilter, Long lTeam) {
        oAuthService.OnlyAdmins();
        Page<ProjectEntity> oPage = null;
        if (lTeam != null) {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oProjectRepository.findByTeamId(lTeam, oPageable);
            } else {
                oPage = oProjectRepository.findByTeamIdAndProject_codeIgnoreCaseContainingOrProject_descriptionIgnoreCaseContainingOrUrlContaining(lTeam, strFilter, strFilter, strFilter, oPageable);
            }
        } else {
            if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                oPage = oProjectRepository.findAll(oPageable);
            } else {
                oPage =  oProjectRepository.findByProject_codeIgnoreCaseContainingOrProject_descriptionIgnoreCaseContainingOrUrlContaining(strFilter, strFilter, strFilter, oPageable);
            }
        }
        return oPage;
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oProjectRepository.count();
    }

    public Long update(ProjectEntity oProjectEntity) {
        validate(oProjectEntity.getId());
        oAuthService.OnlyAdmins();
        return oProjectRepository.save(oProjectEntity).getId();
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdmins();
        oProjectRepository.deleteById(id);
        return id;
    }

    public ProjectEntity getOneRandom() {
        if (count() > 0) {
            ProjectEntity oProjectEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oProjectRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<ProjectEntity> ProjectPage = oProjectRepository.findAll(oPageable);
            List<ProjectEntity> ProjectList = ProjectPage.getContent();
            oProjectEntity = oProjectRepository.getById(ProjectList.get(0).getId());
            return oProjectEntity;
        } else {
            throw new CannotPerformOperationException("No hay projectos en la base de datos");
        }
    }

}
