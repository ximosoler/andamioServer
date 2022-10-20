package net.ausiasmarch.andamio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.TaskEntity;
import net.ausiasmarch.andamio.repository.TaskRepository;

@Service
public class TaskService {

  

    @Autowired
    TaskRepository oTaskRepository;

    public TaskEntity get(Long id) {
      
        return oTaskRepository.getById(id);
    }


}