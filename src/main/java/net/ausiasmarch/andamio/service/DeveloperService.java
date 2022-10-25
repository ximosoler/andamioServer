package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.DeveloperEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.repository.DeveloperRepository;

@Service
public class DeveloperService {
    @Autowired
    DeveloperRepository oDeveloperRepository;

    @Autowired
    AuthService oAuthService;

    public DeveloperEntity get(Long id) {
        try {
            return oDeveloperRepository.findById(id).get();
        } catch (Exception ex) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oDeveloperRepository.count();
    }
}

