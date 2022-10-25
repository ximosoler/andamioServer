package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.DeveloperEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.repository.DeveloperRepository;

@Service
public class DeveloperService {

    private final DeveloperRepository oDeveloperRepository;
    private final AuthService oAuthService;

    @Autowired
    public DeveloperService(DeveloperRepository oDeveloperRepository, AuthService oAuthService) {
        this.oDeveloperRepository = oDeveloperRepository;
        this.oAuthService = oAuthService;
    }

    @Autowired
    AuthService oAuthService;

    public DeveloperEntity get(Long id) {
        return oDeveloperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer with id: " + id + " not found"));
    }

    public Page<DeveloperEntity> getPageByTeam(Long id_team, int page, int size) {
        oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);

        if (id_team == null) {
            return oDeveloperRepository.findAll(oPageable);
        } else {
            return oDeveloperRepository.findByTeamId(id_team, oPageable);
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oDeveloperRepository.count();
    }
}

