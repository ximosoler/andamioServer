package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.TaskEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.repository.TaskRepository;

@Service
public class TaskService {

    @Autowired
    AuthService oAuthService;

    @Autowired
    TaskRepository oTaskRepository;

    public TaskEntity get(Long id) {
        oAuthService.OnlyAdmins();
        return oTaskRepository.getById(id);
    }

    public void validate(Long id) {
        if (!oTaskRepository.existsById(id)) {
            throw new ResourceNotFoundException("id " + id + " not exist");
        }
    }

    public Long delete(Long id) {
        validate(id);
        oAuthService.OnlyAdmins();
        oTaskRepository.deleteById(id);
        return id;
    }

    public Long count() {
        return oTaskRepository.count();
    }


}