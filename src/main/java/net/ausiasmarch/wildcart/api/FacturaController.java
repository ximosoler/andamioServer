package net.ausiasmarch.wildcart.api;

import net.ausiasmarch.wildcart.entity.FacturaEntity;
import net.ausiasmarch.wildcart.service.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/factura")

public class FacturaController {

    @Autowired
    FacturaService oFacturaService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<FacturaEntity>(oFacturaService.get(id), HttpStatus.OK);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        return new ResponseEntity<Long>(oFacturaService.count(), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getPage(
            @PageableDefault(page = 0, size = 10, direction = Sort.Direction.DESC) Pageable oPageable,
            @RequestParam(name = "filter", required = false) String strFilter,
            @RequestParam(name = "usuario", required = false) Long lUsuario) {
        return new ResponseEntity<Page<FacturaEntity>>(oFacturaService.getPage(oPageable, strFilter, lUsuario), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody FacturaEntity oFacturaEntity) {
        return new ResponseEntity<FacturaEntity>(oFacturaService.create(oFacturaEntity), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<?> update(@RequestBody FacturaEntity oFacturaEntity) {
        return new ResponseEntity<FacturaEntity>(oFacturaService.update(oFacturaEntity), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Long>(oFacturaService.delete(id), HttpStatus.OK);
    }

    @PostMapping("/generate/{amount}")
    public ResponseEntity<Long> generate(@PathVariable(value = "amount") int amount) {
        return new ResponseEntity<Long>(oFacturaService.generate(amount), HttpStatus.OK);
    }

}
