package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
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

   
    /**
     * @return returns number of resolutions in database.
     */
    public Long count() {
        oAuthService.OnlyAdmins();
        return oResolutionRepository.count();
    }
    
}
