package net.ausiasmarch.andamio.service;


import net.ausiasmarch.andamio.entity.TeamEntity;
import net.ausiasmarch.andamio.repository.TeamRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamService {
    
    private final TeamRepository oTeamRepository;

    @Autowired
    public TeamService(TeamRepository oTeamRepository) {
        this.oTeamRepository = oTeamRepository;
    }

    public TeamEntity get(Long id) {
        return oTeamRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Team with id: " + id + " not found"));

    }

}
