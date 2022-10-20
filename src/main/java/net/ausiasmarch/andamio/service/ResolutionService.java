package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.ResolutionEntity;
import net.ausiasmarch.andamio.repository.ResolutionRepository;


@Service
public class ResolutionService {

    
    @Autowired
    public ResolutionService(ResolutionRepository oResolutionRepository) {
        this.oResolutionRepository = oResolutionRepository;
    }

    private final ResolutionRepository oResolutionRepository;
    
    public ResolutionEntity get(Long id) {
        return oResolutionRepository.getById(id);
    }
    
}
