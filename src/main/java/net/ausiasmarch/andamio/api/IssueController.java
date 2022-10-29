package net.ausiasmarch.andamio.api;

import net.ausiasmarch.andamio.entity.IssueEntity;
import net.ausiasmarch.andamio.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService oIssueService;

    @Autowired
    public IssueController(IssueService oIssueService) {
        this.oIssueService = oIssueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<IssueEntity> get(@PathVariable Long id) {
        return new ResponseEntity<>(oIssueService.get(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oIssueService.delete(id), HttpStatus.OK);
    }
}
