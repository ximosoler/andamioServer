package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.IssueEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IssueService {

    private final IssueRepository oIssueRepository;

    @Autowired
    public IssueService(IssueRepository oIssueRepository) {
        this.oIssueRepository = oIssueRepository;
    }

    @Autowired
    AuthService oAuthService;

    public void validate(Long id) {
        if (!oIssueRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public IssueEntity get(Long id) {
        return oIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue with id: " + id + " not found"));
    }

    public Long update(IssueEntity oIssueEntity) {
        validate(oIssueEntity.getId());
        oAuthService.OnlyAdmins();
        return oIssueRepository.save(oIssueEntity).getId();
    }
}
