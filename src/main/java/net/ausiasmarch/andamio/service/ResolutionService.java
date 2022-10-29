package net.ausiasmarch.andamio.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.ResolutionEntity;
import net.ausiasmarch.andamio.repository.ResolutionRepository;


@Service
public class ResolutionService {

    
    @Autowired
    public ResolutionService(ResolutionRepository oResolutionRepository, AuthService oAuthService) {
        this.oResolutionRepository = oResolutionRepository;
        this.oAuthService = oAuthService;
    }

    private final ResolutionRepository oResolutionRepository;
    private final AuthService oAuthService;
    
    public ResolutionEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oResolutionRepository.getById(id);
    }

    public Page<ResolutionEntity> getPage(String strFilter, Long id_issue, Long id_developer, Pageable oPageable) {
        oAuthService.OnlyAdmins();
        if (strFilter == null && id_issue == null && id_developer == null) {
            return oResolutionRepository.findAll(oPageable);
        } else if (id_developer == null && id_issue == null) {
            return oResolutionRepository.findByObservationsIgnoreCaseContaining(strFilter, oPageable);
        } else if (strFilter == null && id_developer == null) {
            return oResolutionRepository.findByIssueId(id_issue, oPageable);
        } else if (strFilter == null && id_issue == null) {
            return oResolutionRepository.findByDeveloperId(id_developer, oPageable);
        } else if (id_developer == null) {
            return oResolutionRepository.findByObservationsIgnoreCaseContainingAndIssueId(strFilter, id_issue, oPageable);
        } else if (id_issue == null) {
            return oResolutionRepository.findByObservationsIgnoreCaseContainingAndDeveloperId(strFilter, id_developer, oPageable);
        } else if (strFilter == null) {
            return oResolutionRepository.findByIssueIdAndDeveloperId(id_issue, id_developer, oPageable);
        } else {
            return oResolutionRepository.findByObservationsIgnoreCaseContainingAndIssueIdAndDeveloperId(strFilter, id_issue, id_developer, oPageable);
        }
    }
    
}
