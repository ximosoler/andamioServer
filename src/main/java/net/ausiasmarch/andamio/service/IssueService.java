package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.IssueEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.repository.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public IssueEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oIssueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Issue with id: " + id + " not found"));
    }

    public void validate(Long id) {
        if (!oIssueRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }
    public Long delete(Long id) {
        oAuthService.OnlyAdmins();
        validate(id);
        oIssueRepository.deleteById(id);
        return id;
    }
}
