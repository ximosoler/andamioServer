package net.ausiasmarch.andamio.service;

import net.ausiasmarch.andamio.entity.IssueEntity;
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
}
