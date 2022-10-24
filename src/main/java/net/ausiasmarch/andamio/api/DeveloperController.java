package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import net.ausiasmarch.andamio.entity.DeveloperEntity;
import net.ausiasmarch.andamio.service.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    private final DeveloperService oDeveloperService;

    @Autowired
    public DeveloperController(DeveloperService oDeveloperService) {
        this.oDeveloperService = oDeveloperService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(oDeveloperService.get(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Page<DeveloperEntity>> getPageByTeam(
            @RequestParam(value = "team", required = false) Long id_team,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size) {
        return new ResponseEntity<>(oDeveloperService.getPageByTeam(id_team, page, size), HttpStatus.OK);
    }
}
