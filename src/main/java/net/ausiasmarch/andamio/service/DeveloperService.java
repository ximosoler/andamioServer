package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import net.ausiasmarch.andamio.entity.DeveloperEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.exception.ResourceNotModifiedException;
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

    public void validate(Long id) {
        if (!oDeveloperRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public DeveloperEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oDeveloperRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Developer with id: " + id + " not found"));
    }

    public Page<DeveloperEntity> getPage(Long id_team, Long id_usertype, int page, int size) {
        oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_team == null && id_usertype == null) {
            return oDeveloperRepository.findAll(oPageable);
        } else if (id_team == null) {
            return oDeveloperRepository.findByUsertypeId(id_usertype, oPageable);
        } else if (id_usertype == null) {
            return oDeveloperRepository.findByTeamId(id_team, oPageable);
        } else {
            return oDeveloperRepository.findByTeamIdAndUsertypeId(id_team, id_usertype, oPageable);
        }
    }

    public Long count() {
        oAuthService.OnlyAdmins();
        return oDeveloperRepository.count();
    }

    public Long update(DeveloperEntity oDeveloperEntity) {
        validate(oDeveloperEntity.getId());
        oAuthService.OnlyAdmins();
        return oDeveloperRepository.save(oDeveloperEntity).getId();
    }

    public Long create(DeveloperEntity oNewDeveloperEntity) {
        oAuthService.OnlyAdmins();
        oNewDeveloperEntity.setId(0L);
        return oDeveloperRepository.save(oNewDeveloperEntity).getId();
    }

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oDeveloperRepository.deleteById(id);
        if (oDeveloperRepository.existsById(id)) {
            throw new ResourceNotModifiedException("can't remove register " + id);
        } else {
            return id;
        }
    }
}
