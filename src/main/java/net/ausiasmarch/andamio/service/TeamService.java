package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.TeamEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.exception.UnauthorizedException;
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
        oAuthService.OnlyAdmins();
        return oTeamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team with id: " + id + " not found"));
    }

    public void validate(Long id) {
        if (!oTeamRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdmins();
        oTeamRepository.deleteById(id);
        return id;
    }

    public Page<TeamEntity> getPage(Pageable oPageable, String strFilter, Long lDeveloper) {
        Page<TeamEntity> oPage = null;
        if (oAuthService.isAdmin()) {
            if (lDeveloper != null) {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oTeamRepository.findByDeveloperId(lDeveloper, oPageable);
                } else {
                    return oTeamRepository.findByDeveloperIdAndNameContainingIgnoreCase(lDeveloper, strFilter, oPageable);
                }
            } else {
                if (strFilter == null || strFilter.isEmpty() || strFilter.trim().isEmpty()) {
                    return oTeamRepository.findAll(oPageable);
                } else {
                    return oTeamRepository.findByNameContainingIgnoreCase(strFilter, oPageable);
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to admin role");
        }
    }

    public Long create(TeamEntity oNewTeamEntity) {
        oAuthService.OnlyAdmins();
        oNewTeamEntity.setId(0L);
        return oTeamRepository.save(oNewTeamEntity).getId();
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oTeamRepository.count();
        }
}
