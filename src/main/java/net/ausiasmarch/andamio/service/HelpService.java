package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.HelpEntity;
import net.ausiasmarch.andamio.repository.HelpRepository;

@Service
public class HelpService {

    @Autowired
    public HelpService(HelpRepository oHelpRepository, AuthService oAuthService) {
        this.oHelpRepository = oHelpRepository;
        this.oAuthService = oAuthService;
    }
    private final HelpRepository oHelpRepository;
    private final AuthService oAuthService;
    
    public HelpEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oHelpRepository.getById(id);
    }
}
