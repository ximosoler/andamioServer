package net.ausiasmarch.andamio.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.ausiasmarch.andamio.entity.TaskEntity;
import net.ausiasmarch.andamio.exception.ResourceNotFoundException;
import net.ausiasmarch.andamio.helper.RandomHelper;
import net.ausiasmarch.andamio.repository.TaskRepository;

@Service
public class TaskService {

    private final String[] DESCRIPTION = { "SQL db test", "Inno db is cool", "administrador SQL test",
            "MongoDB", "Hola Mundo!", "Adios Mundo!", "Say Hello!", "My cat bigotillos", "The mexican", "Another one" };

    @Autowired
    AuthService oAuthService;

    @Autowired
    TaskRepository oTaskRepository;

    @Autowired
    ProjectService oProjectService;

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
        oAuthService.OnlyAdmins();
        return oTaskRepository.count();
    }

    public Long update(TaskEntity oTaskEntity) {
        validate(oTaskEntity.getId());
        oAuthService.OnlyAdmins();
        oTaskRepository.save(oTaskEntity);
        return oTaskEntity.getId();
    }

    public TaskEntity generate() {
        oAuthService.OnlyAdmins();
        TaskEntity oTaskEntity = generateRandomTask();
        oTaskRepository.save(oTaskEntity);
        return oTaskEntity;
    }

    public Long generateSome(Integer amount) {
        oAuthService.OnlyAdmins();
        List<TaskEntity> userList = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            TaskEntity oTaskEntity = generateRandomTask();
            oTaskRepository.save(oTaskEntity);
            userList.add(oTaskEntity);
        }
        return oTaskRepository.count();
    }

    private TaskEntity generateRandomTask() {
        TaskEntity oTaskEntity = new TaskEntity();
        oTaskEntity.setDescription(generateDescription());
        oTaskEntity.setComplexity(RandomHelper.getRandomInt(1, 10));
        oTaskEntity.setPriority(RandomHelper.getRandomInt(1, 10));
        oTaskEntity.setProject(oProjectService.getOneRandom());
        return oTaskEntity;
    }

    private String generateDescription() {
        return DESCRIPTION[RandomHelper.getRandomInt(0, DESCRIPTION.length - 1)].toLowerCase();
    }
}