package net.ausiasmarch.andamio.api;

import net.ausiasmarch.andamio.entity.ProjectEntity;
import net.ausiasmarch.andamio.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/project")
public class ProjectController {

    
    private final ProjectService oProjectService;
    
    @Autowired
    public ProjectController(ProjectService oProjectService) {
        this.oProjectService = oProjectService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ProjectEntity>(oProjectService.get(id), HttpStatus.OK);
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oProjectService.count(), HttpStatus.OK);
    }
    
    @PutMapping("")
    public ResponseEntity<Long> update(@RequestBody ProjectEntity oProjectEntity) {
        return new ResponseEntity<Long>(oProjectService.update(oProjectEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable(value = "id") Long id){
        return oProjectService.delete(id);
    }
    
}
