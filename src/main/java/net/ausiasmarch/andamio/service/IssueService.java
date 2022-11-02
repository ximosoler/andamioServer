package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.IssueEntity;
import net.ausiasmarch.andamio.exception.CannotPerformOperationException;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.helper.RandomHelper;
import net.ausiasmarch.andamio.repository.IssueRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    private final IssueRepository oIssueRepository;
    private final AuthService oAuthService;

    @Autowired
    public IssueService(IssueRepository oIssueRepository, AuthService oAuthService) {
        this.oIssueRepository = oIssueRepository;
        this.oAuthService = oAuthService;
    }

    public void validate(Long id) {
        if (!oIssueRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public IssueEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue with id: " + id + " not found"));
    }

    public Long update(IssueEntity oIssueEntity) {
        validate(oIssueEntity.getId());
        oAuthService.OnlyAdmins();
        return oIssueRepository.save(oIssueEntity).getId();
    }

    public Page<IssueEntity> getPage(Long id_developer, Long id_task, int page, int size) {
        oAuthService.OnlyAdmins();
        Pageable oPageable = PageRequest.of(page, size);
        if (id_developer == null && id_task == null) {
            return oIssueRepository.findAll(oPageable);
        } else if (id_developer == null) {
            return oIssueRepository.findByTaskId(id_task, oPageable);
        } else if (id_task == null) {
            return oIssueRepository.findByDeveloperId(id_developer, oPageable);
        } else {
            return oIssueRepository.findByDeveloperIdAndTaskId(id_developer, id_task, oPageable);
        }
    }
        

    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oIssueRepository.deleteById(id);
        return id;
    }


    public Long create(IssueEntity oNewIssueEntity) {
        oAuthService.OnlyAdmins();
        oNewIssueEntity.setId(0L);
        return oIssueRepository.save(oNewIssueEntity).getId();
    }
    
    public Long count(){
        oAuthService.OnlyAdmins();
        return oIssueRepository.count();

    }

    public IssueEntity getOneRandom() {
        if (count() > 0) {
            IssueEntity oIssueEntity = null;
            int iPosicion = RandomHelper.getRandomInt(0, (int) oIssueRepository.count() - 1);
            Pageable oPageable = PageRequest.of(iPosicion, 1);
            Page<IssueEntity> issuePage = oIssueRepository.findAll(oPageable);
            List<IssueEntity> issueList = issuePage.getContent();
            oIssueEntity = oIssueRepository.getById(issueList.get(0).getId());
            return oIssueEntity;
        } else {
            throw new CannotPerformOperationException("ho hay usuarios en la base de datos");
        }
    }

}
