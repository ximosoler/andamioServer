package net.ausiasmarch.andamio.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ausiasmarch.andamio.entity.ResolutionEntity;
import net.ausiasmarch.andamio.service.ResolutionService;

@RestController
@RequestMapping("/resolution")
public class ResolutionController {

    @Autowired
    ResolutionService oResolutionService;

    @GetMapping("/{id}")
    public ResponseEntity<ResolutionEntity> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<ResolutionEntity>(oResolutionService.get(id), HttpStatus.OK);
    }
}
