package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.HelpEntity;
import net.ausiasmarch.andamio.repository.HelpRepository;

@Service
public class HelpService {
    
    @Autowired
    public HelpService(HelpRepository oHelpRepository) {
        this.oHelpRepository = oHelpRepository;
    }

    private final HelpRepository oHelpRepository;
    
    public HelpEntity get(Long id) {
        return oHelpRepository.getById(id);
    }
}
