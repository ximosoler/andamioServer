package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.andamio.entity.TeamEntity;
import net.ausiasmarch.andamio.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {

    
    private final TeamService oTeamService;
    
    @Autowired
    public TeamController(TeamService oTeamService) {
        this.oTeamService = oTeamService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oTeamService.get(id), HttpStatus.OK);
    }
    
    @DeleteMapping("/{id}")
    public Long delete(@PathVariable(value = "id") Long id){
        return oTeamService.delete(id);
    }
 
    
}