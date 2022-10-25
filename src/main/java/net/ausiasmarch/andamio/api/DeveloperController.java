package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import net.ausiasmarch.andamio.entity.DeveloperEntity;
import net.ausiasmarch.andamio.service.DeveloperService;
import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("/developer")
public class DeveloperController {

    @Autowired
    DeveloperService oDeveloperService;

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<DeveloperEntity>(oDeveloperService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oDeveloperService.count(), HttpStatus.OK);
    }

}
