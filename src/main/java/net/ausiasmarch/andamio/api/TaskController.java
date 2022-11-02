package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable(value = "id") Long id){
        return oTaskService.delete(id);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oTaskService.count(), HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<Long> update(@RequestBody TaskEntity oTaskEntity) {
        return new ResponseEntity<>(oTaskService.update(oTaskEntity), HttpStatus.OK);
    }

    @PostMapping("/generate")
    public ResponseEntity<TaskEntity> generate() {
        return new ResponseEntity<TaskEntity>(oTaskService.generate(), HttpStatus.OK);
    }
    
    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generateSome(@PathVariable(value = "amount") Integer amount) {
        return new ResponseEntity<>(oTaskService.generateSome(amount), HttpStatus.OK);
    }
}
