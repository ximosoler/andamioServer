package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.andamio.entity.TaskEntity;
import net.ausiasmarch.andamio.service.TaskService;

@RestController
@RequestMapping("/task")
public class TaskController {
    

    @Autowired
    TaskService oTaskService;

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> get(@PathVariable(value = "id") Long id) {

        return new ResponseEntity<TaskEntity>(oTaskService.get(id), HttpStatus.OK);

    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTaskService.count(), HttpStatus.OK);
    }


}
