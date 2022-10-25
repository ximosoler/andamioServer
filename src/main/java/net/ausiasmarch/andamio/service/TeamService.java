package net.ausiasmarch.andamio.service;


import net.ausiasmarch.andamio.entity.TeamEntity;
import net.ausiasmarch.andamio.repository.TeamRepository;

import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    
    private final TeamRepository oTeamRepository;
    private final AuthService oAuthService;

    @Autowired
    public TeamService(TeamRepository oTeamRepository, AuthService oAuthService) {
        this.oTeamRepository = oTeamRepository;
        this.oAuthService = oAuthService;
    }

    

    public TeamEntity get(Long id) {
        return oTeamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team with id: " + id + " not found"));

    }

    public Page<TeamEntity> getPage(Pageable oPageable) {
        Page<TeamEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            oPage= oTeamRepository.findAll(oPageable);
        } 
        return oPage;
    }

}

